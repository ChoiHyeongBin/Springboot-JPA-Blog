package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository	// 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM user WHERE username = 1?;
	// Optional<User> findByUsername(String username);		// 함수 이름 작성 시 중간의 객체 이름 첫 글자는 대문자로 지정해줘야 됨 ------> 기존
	public User findByUsernameOrderByUsernameAsc(String username);		// findByUsername 으로 같은 이름이 사용 불가해서 임시로 Order By 추가
	
	Optional<User> findByUsername(String username);
	
}


// JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2;
// User findByUsernameAndPassword(String username, String password);

//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);
