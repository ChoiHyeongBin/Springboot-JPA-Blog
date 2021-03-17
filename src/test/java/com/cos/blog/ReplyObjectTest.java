package com.cos.blog;

import org.junit.jupiter.api.Test;
// import org.junit.Test;		// 실행 안됨

import com.cos.blog.model.Reply;

public class ReplyObjectTest {

	@Test
	public void toStringTest() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println("ReplyObjectTest reply : " + reply);		// 오브젝트 출력 시에 toString() 이 자동 호출됨
	}
	
}
