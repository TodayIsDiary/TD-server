package com.example.todayisdiary.domain.board.service;

import com.example.todayisdiary.domain.board.dto.BoardList;
import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.dto.BoardResponseList;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.like.entity.BoardLove;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.global.date.DateService;
import com.example.todayisdiary.global.s3.facade.S3Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final S3Facade s3Facade;
    private final DateService dateService;

    @Transactional
    @Override
    public void createBoard(BoardRequest boardRequest) {

        User user = userFacade.getCurrentUser();

        Board board = new Board(
                boardRequest.getTitle(),
                boardRequest.getContent(),
                boardRequest.getCategory(),
                user,
                boardRequest.getImageUrl()
        );

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void setBoard(BoardRequest boardRequest, Long id) {
        Board board = boardFacade.getBoardById(id);
        userMatch(board);

        board.setBord(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getCategory());
        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void delBoard(Long id) {
        Board board = boardFacade.getBoardById(id);
        userMatch(board);
        s3Facade.delBoard(board);

        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseList boardLists() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boards = boardFacade.getBoardAllById(sort);
        List<BoardList> boardLists = new ArrayList<>();

        for (Board board : boards) {
            BoardList dto = BoardList.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .date(dateService.betweenDate(board.getBoardTime()))
                    .view(board.getView())
                    .commentCount(board.getComments().size())
                    .imageUrl(s3Facade.getUrl(board.getImageUrl()))
                    .build();
            boardLists.add(dto);
        }
        return new BoardResponseList(boardLists);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseList boardCategoryList(BoardCategory category) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boards = boardFacade.getBoardAllById(sort);
        List<BoardList> boardLists = new ArrayList<>();

        for (Board board : boards) {
            if (board.getCategory().equals(category)) {
                BoardList dto = BoardList.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .category(board.getCategory())
                        .date(dateService.betweenDate(board.getBoardTime()))
                        .view(board.getView())
                        .commentCount(board.getComments().size())
                        .imageUrl(s3Facade.getUrl(board.getImageUrl()))
                        .build();
                boardLists.add(dto);
            }
        }
        return new BoardResponseList(boardLists);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseList boardHeartList() {
        Sort sort = Sort.by(Sort.Direction.DESC, "heart");
        List<Board> boards = boardFacade.getBoardAllById(sort);
        List<BoardList> boardLists = new ArrayList<>();

        for (Board board : boards) {
            BoardList dto = BoardList.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .date(dateService.betweenDate(board.getBoardTime()))
                    .view(board.getView())
                    .commentCount(board.getComments().size())
                    .imageUrl(s3Facade.getUrl(board.getImageUrl()))
                    .build();
            boardLists.add(dto);
        }
        return new BoardResponseList(boardLists);
    }

    // ????????? ????????? ????????? ?????????, GET, /user/title-list
    @Transactional(readOnly = true)
    @Override
    public BoardResponseList myPost() {
        User user = userFacade.getCurrentUser();
        return boardUserList(user);
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponseList getPost(Long id){
        User user = userFacade.getUserById(id);
        return boardUserList(user);
    }

    @Transactional
    @Override
    public BoardResponse boardDetail(Long id) {
        Board board = boardFacade.getBoardById(id);
        board.addView();

        boardRepository.save(board);
        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getTitle())
                .category(board.getCategory())
                .boardTime(dateService.betweenDate(board.getBoardTime()))
                .writer(board.getUser().getNickName())
                .isLiked(writerLike(board))
                .commentCount(board.getComments().size())
                .heart(board.getHeart())
                .imageUrl(s3Facade.getUrl(board.getImageUrl()))
                .userId(board.getUser().getId()).build();
    }

    private void userMatch(Board board) {
        if (board.getUser().getAccountId().equals(userFacade.getCurrentUser().getAccountId()) || userFacade.getCurrentUser().getRole() == Role.ROLE_ADMIN) {
        } else throw new IllegalStateException("????????? ????????????.");
    }

    private boolean writerLike(Board board){
        User user = userFacade.getCurrentUser();
        List<BoardLove> boardLoves = board.getBoardLoves();

        for(BoardLove boardLove : boardLoves){
            if(boardLove.getUser().getAccountId().equals(user.getAccountId())){
                return true;
            }
        }
        return false;
    }

    private BoardResponseList boardUserList(User user){
        List<Board> boards = user.getBoards();
        List<BoardList> boardLists = new ArrayList<>();

        for (Board board : boards) {
            BoardList dto = BoardList.builder()
                    .boardId(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .date(dateService.betweenDate(board.getBoardTime()))
                    .view(board.getView())
                    .commentCount(board.getComments().size())
                    .imageUrl(s3Facade.getUrl(board.getImageUrl()))
                    .build();
            boardLists.add(dto);
        }
        return new BoardResponseList(boardLists);
    }

}
