package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) {		// @ResponseBody 붙이는 이유 -> Data 를 리턴해주는 컨트롤러 함수
		
		// POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// Retrofit2 (안드로이드에서 많이 사용)
		// OkHttp
		// RestTemplate
		
		// HttpHeader 오브젝트 생성
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");	// 웬만하면 value 값은 변수화시켜서 사용하는게 좋음
		params.add("client_id", "752d31d8d512335fc564541ca5649f9d");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 		// kakaoTokenRequest -> Http body 와 header 값이 들어있음
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", 
				HttpMethod.POST, 
				kakaoTokenRequest, 
				String.class
		);
		
		return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답 : " + response;
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}
	
}
