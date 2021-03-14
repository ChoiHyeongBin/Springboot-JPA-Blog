package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob	// 대용량 데이터
	private String content;	// 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨
	
	private int count;	// 조회수
	
	@ManyToOne(fetch = FetchType.EAGER)		// Many = Board, User = One (한 명의 유저는 여러 개의 게시글을 쓸 수 있음)
	@JoinColumn(name = "userId")
	private User user;	// DB는 오브젝트를 저장할 수 없음. FK, 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)	// mappedBy 연관관계의 주인이 아님 (난 FK가 아니에요) DB에 컬럼을 만들지 마세요.
	@JsonIgnoreProperties({"board"})	// 무한참조 방지
	@OrderBy("id desc")
	private List<Reply> replys;	// 나중에 select 하기 위해서 있는 코드
	
	@CreationTimestamp		// 현재 시간이 자동으로 들어감
	private Timestamp createDate;
}
