package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IoC 를 해줌(메모리에 대신 띄워준다는 의미)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;	// @Autowired 설정 시 userRepository 에 객체가 들어오게 됨
	
	@Transactional
	public int 회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : 회원가입() : " + e.getMessage());
		}
		
		return -1;
	}
}
