package com.cos.blog.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetail;
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
			System.out.println("PrincipalOauth2UserService -> getAttributes : " + oauth2User.getAttributes());

		String provider = userRequest.getClientRegistration().getRegistrationId();		// facebook
			System.out.println("PrincipalOauth2UserService -> provider : " + provider);
		String providerId = oauth2User.getAttribute("sub");		// null 값
			System.out.println("PrincipalOauth2UserService -> providerId : " + providerId);	
		String username = provider + "_" + providerId;		// facebook_116522453331692469215
			System.out.println("PrincipalOauth2UserService -> username : " + username);
		String password = bCryptPasswordEncoder.encode("dummy");
			System.out.println("PrincipalOauth2UserService -> password : " + password);
		String email = oauth2User.getAttribute("email");
			System.out.println("PrincipalOauth2UserService -> email : " + email);
		RoleType role = RoleType.ROLE_USER;
		
		User userEntity = userRepository.findByUsernameOrderByUsernameAsc(username);
			System.out.println("PrincipalOauth2UserService -> userEntity : " + userEntity);
		
		if (userEntity == null) {
			System.out.println("페이스북 로그인이 최초입니다.");
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
			System.out.println("페이스북 로그인을 이미 한적이 있습니다. 당신은 자동회원가입이 되어 있습니다.");
		}
			
		return new PrincipalDetail(userEntity, oauth2User.getAttributes());
	}
	
}
