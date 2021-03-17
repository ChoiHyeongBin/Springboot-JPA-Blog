package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	
	@Modifying	// @Query 로 작성 된 변경, 삭제 쿼리 메서드를 사용할 때 필요 (INSERT, UPDATE, DELETE, DDL)
	@Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)	// ReplySaveRequestDto 의 객체 순서에 맞게 써야 함, nativeQuery -> 내가 작성한 쿼리가 작동되게 함
	int mSave(int userId, int boardId, String content);	// 업데이트된 행의 개수를 리턴해줌
	
}
