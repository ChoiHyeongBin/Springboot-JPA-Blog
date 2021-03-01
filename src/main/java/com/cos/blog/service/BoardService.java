package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;	// @Autowired 설정 시 boardRepository 에 객체가 들어오게 됨

	@Transactional
	public void boardWrite(Board board, User user) {		// title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

}
