package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice	// 모든 Exception 이 발생하면 이 클래스에 들어오게 됨
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)	// IllegalArgumentException 라는 예외가 발생하면 IllegalArgumentException e 에 전달해줌
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
