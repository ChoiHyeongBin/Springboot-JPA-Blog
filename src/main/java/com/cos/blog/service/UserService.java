package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean 에 등록을 해줌. IoC 를 해줌(메모리에 대신 띄워준다는 의미)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;	// @Autowired 설정 시 userRepository 에 객체가 들어오게 됨
	
	@Autowired	// encoder 가 DI 가 되서 주입이 됨
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)		// true 인 경우 insert, update, delete 실행 시 예외 발생, 기본 설정은 false
	// 성능을 최적화하기 위해 사용할 수도 있고 특정 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용할 수도 있음
	public User memberFind(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{	// 회원을 찾았는데 없으면 빈 객체를 리턴
			return new User();
		});
		
		return user;
	}

	@Transactional
	public void joinMember(User user) {
			// System.out.println("UserService user : " + user);
		String rawPassword = user.getPassword();		// 1234 원문
		String encPassword = encoder.encode(rawPassword);	// 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void memberUpdate(User user) {
		// 수정 시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select 를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서임
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update 문을 날려줌
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// Validate 체크 => oauth 필드에 값이 없으면 수정 가능
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {	// 일반 사용자라면
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}

		// 회원수정 함수 종료 시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됨
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update 문을 날려줌
	}
	
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
	
	/*
	@Transactional(readOnly = true)		// Select 할 때 트랜잭션 시작, 서비스 종료 시에 트랜잭션 종료 (정합성)
	public User login(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	*/
	
}
