package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice	// 모든 Exception 이 발생하면 이 클래스에 들어오게 됨
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)	// IllegalArgumentException 라는 예외가 발생하면 IllegalArgumentException e 에 전달해줌
	public String handleArgumentException(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
}
