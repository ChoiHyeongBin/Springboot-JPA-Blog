package com.cos.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor		// 의존성 주입 3
public class BoardService {

	private final BoardRepository boardRepository;		// 의존성 주입 3
	private final ReplyRepository replyRepository;			// 의존성 주입 3
	
	/* 
	// 의존성 주입 2
	private BoardRepository boardRepository;
	private ReplyRepository replyRepository;
	
	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
		this.boardRepository = bRepo;
		this.replyRepository = rRepo;
	}
	*/
	
	/*
	// 기존 의존성 주입
	@Autowired
	private BoardRepository boardRepository;	// @Autowired 설정 시 boardRepository 에 객체가 들어오게 됨
	
	@Autowired
	private ReplyRepository replyRepository;
	*/

	@Transactional
	public void boardWrite(Board board, User user) {		// title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board boardDetailView(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void boardDelete(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void boardUpdate(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});	// 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료 시(Service 가 종료될 때) 트랜잭션이 종료됨. 이때 더티체킹 - 자동 업데이트가 됨. DB flush
	}
	
	@Transactional
	public void replySave(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
			System.out.println("BoardService result : " + result);
	}
	
	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	/* replySave -> 1번째 방법
	@Transactional
	public void replySave(ReplySaveRequestDto replySaveRequestDto) {
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id 를 찾을 수 없습니다.");
		});	// 영속화 완료
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id 를 찾을 수 없습니다.");
		});	// 영속화 완료
		
		Reply reply = Reply.builder()
				.user(user)
				.board(board)
				.content(replySaveRequestDto.getContent())
				.build();
		
		replyRepository.save(reply);
	}
	*/

}
