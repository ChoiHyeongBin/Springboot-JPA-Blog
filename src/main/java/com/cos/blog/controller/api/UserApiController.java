package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {		// username, password, email
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB 에 insert 를 하고 아래에서 return 이 되면 됨
		userService.joinMember(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);	// 자바오브젝트를 JSON 으로 변환해서 리턴 (Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {	// @RequestBody 를 선언하지 않으면 이렇게 받게 됨 -> key=value, x-www-form-urlencoded
		userService.memberUpdate(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경해줄 것임
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	/*		
	// 전통적인 방식의 로그인
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
		System.out.println("UserApiController : login 호출됨");
		User principal = userService.login(user);		// principal (접근주체)
		
		if (principal != null) {
			session.setAttribute("principal", principal);
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	*/
	
	/*		// 회원정보 수정완료 후 회원정보 들어가면 정보가 안 바뀌어 있음
	Authentication authentication = 	new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
	SecurityContext securityContext = SecurityContextHolder.getContext();
	securityContext.setAuthentication(authentication);
	
	session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	*/
	
}
