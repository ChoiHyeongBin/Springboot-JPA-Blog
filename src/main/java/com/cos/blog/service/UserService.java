package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IoC 를 해줌(메모리에 대신 띄워준다는 의미)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;	// @Autowired 설정 시 userRepository 에 객체가 들어오게 됨
	
	/*
	@Transactional
	public int joinMember(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService : joinMember() : " + e.getMessage());
		}
		
		return -1;
	}
	*/
	
	@Transactional
	public void joinMember(User user) {
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)		// Select 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료 (정합성)
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
}
