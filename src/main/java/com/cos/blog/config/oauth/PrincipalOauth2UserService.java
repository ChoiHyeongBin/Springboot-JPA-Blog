package com.cos.blog.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			System.out.println("PrincipalOauth2UserService userRequest : " + userRequest.getClientRegistration());
			System.out.println("PrincipalOauth2UserService userRequest : " + userRequest.getAccessToken());
			System.out.println("PrincipalOauth2UserService userRequest : " + super.loadUser(userRequest).getAttributes());
		return super.loadUser(userRequest);
	}
	
}
