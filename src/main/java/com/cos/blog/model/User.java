package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder	// 빌더 패턴
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity		// User 클래스가 MySQL 에 테이블이 생성이 됨
public class User {
	
	@Id		// Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// 프로젝트에서 연결된 DB의 넘버링 전략을 따라감
	private int id;		// 시퀀스(Oracle), auto_increment(MySQL)
	
	@Column(nullable = false, length = 30)
	private String username;		// 아이디
	
	@Column(nullable = false, length = 100)	// 123456 => 해쉬 (비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'")
	private String role;		// Enum 을 쓰는게 좋음 // admin, user, manager
	
	@CreationTimestamp		// 시간이 자동 입력
	private Timestamp createDate;
}
