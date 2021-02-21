package com.cos.blog.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;

@RestController
public class UserApiController {

	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB 에 insert 를 하고 아래에서 return 이 되면 됨
		return new ResponseDto<Integer>(HttpStatus.OK, 1);	// 자바오브젝트를 JSON 으로 변환해서 리턴 (Jackson)
	}
}
