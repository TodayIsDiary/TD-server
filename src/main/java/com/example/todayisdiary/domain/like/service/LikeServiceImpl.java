package com.example.todayisdiary.domain.like.service;

import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.like.entity.BoardLike;
import com.example.todayisdiary.domain.like.repository.BoardLikeRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void boardLike(Long diaryId){

        User user = userFacade.getCurrentUser();
        Board board = boardFacade.getBoardById(diaryId);

        board.likes();

        BoardLike boardLike = BoardLike.builder()
                .user(user)
                .board(board).build();
        board.addHeart();
        boardRepository.save(board);
        boardLikeRepository.save(boardLike);
    }

    @Override
    @Transactional
    public void boardLikeCancel(Long diaryId){

        User user = userFacade.getCurrentUser();
        Board board = boardFacade.getBoardById(diaryId);
        List<BoardLike> like = board.getLikes();

        for (BoardLike boardLike : like) {
            if (boardLike.getUser().equals(user)) {
                board.unlikes();

                board.deleteHeart();
                boardRepository.save(board);
                boardLikeRepository.delete(boardLike);
            }
        }
    }
}
