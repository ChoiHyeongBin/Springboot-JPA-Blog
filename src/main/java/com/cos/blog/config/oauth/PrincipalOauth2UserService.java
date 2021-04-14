package com.cos.blog.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.oauth.provider.FacebookUserInfo;
import com.cos.blog.config.oauth.provider.GoogleUserInfo;
import com.cos.blog.config.oauth.provider.NaverUserInfo;
import com.cos.blog.config.oauth.provider.OAuth2UserInfo;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			System.out.println("PrincipalOauth2UserService userRequest : " + userRequest);
			System.out.println("PrincipalOauth2UserService getClientRegistration : " + userRequest.getClientRegistration());		// registrationId 로 어떤 OAuth 로 로그인 했는지 확인가능
			System.out.println("PrincipalOauth2UserService getAccessToken : " + userRequest.getAccessToken().getTokenValue());
			
		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code 를 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
		// userRequest -> loadUser 함수 호출 -> 구글로부터 회원프로필을 받아줌
			System.out.println("PrincipalOauth2UserService -> getAttributes : " + oauth2User.getAttributes());
			
		OAuth2UserInfo oAuth2UserInfo = null;
		
		// 유지보수에 용이
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		} else {
			System.out.println("구글, 페이스북, 네이버만 지원합니다.");
		}

		// oAuth2UserInfo 객체만 있으면 밑의 코드가 정상 작동함
//		String provider = userRequest.getClientRegistration().getRegistrationId();		// facebook (방법1 : oAuth2UserInfo 객체 없을 시 사용)
//			System.out.println("PrincipalOauth2UserService -> provider : " + provider);
		
		String provider = oAuth2UserInfo.getProvider();	
		
//		String providerId = oauth2User.getAttribute("sub");		// null 값 (facebook 로그인 시 sub 가 아니기 때문에 받아올 수없다, 방법1)
//			System.out.println("PrincipalOauth2UserService -> providerId : " + providerId);	
		
		String providerId = oAuth2UserInfo.getProviderId();
			
		String username = provider + "_" + providerId;		// facebook_116522453331692469215
			System.out.println("PrincipalOauth2UserService -> username : " + username);
			
		String password = bCryptPasswordEncoder.encode("dummy");
			System.out.println("PrincipalOauth2UserService -> password : " + password);
			
		String email = 	oAuth2UserInfo.getEmail();
			
//		String email = oauth2User.getAttribute("email");
//			System.out.println("PrincipalOauth2UserService -> email : " + email);
			
		RoleType role = RoleType.ROLE_USER;
		
		User userEntity = userRepository.findByUsernameOrderByUsernameAsc(username);
			System.out.println("PrincipalOauth2UserService -> userEntity : " + userEntity);
		
		if (userEntity == null) {
			System.out.println("OAuth 로그인이 최초입니다.");
			userEntity = User.builder()	// Optional.ofNullable() -> value 가 null 인 경우 비어있는 Optional 을 반환함. 값이 null 일수도 있는 것은 해당 메서드를 사용 (삭제)
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
				System.out.println("PrincipalOauth2UserService -> save(userEntity) : " + userEntity);
		} else {
			System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
		}
			
		return new PrincipalDetail(userEntity, oauth2User.getAttributes());
	}
	
}
