package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder	// 빌더 패턴
// ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity		// User 클래스가 MySQL 에 테이블이 생성이 됨
// @DynamicInsert		// insert 시에 null 인 필드를 제외시켜줌
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

	// @ColumnDefault("user")
	// DB 는 RoleType 이라는 게 없음
	@Enumerated(EnumType.STRING)
	private RoleType role;		// Enum 을 쓰는게 좋음 // ADMIN, USER
	
	@CreationTimestamp		// 시간이 자동 입력 (현재 시간을 createDate 에 넣어줌)
	private Timestamp createDate;
}
