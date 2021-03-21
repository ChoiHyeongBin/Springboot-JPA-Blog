package com.cos.blog;

import org.junit.jupiter.api.Test;
// import org.junit.Test;		// 실행 안됨 -> JUnit 5 에서 실행가능한 듯 (라이브러리가 달라서 안된다고 함)

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
