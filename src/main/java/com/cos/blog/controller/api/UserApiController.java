package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
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
	
}
