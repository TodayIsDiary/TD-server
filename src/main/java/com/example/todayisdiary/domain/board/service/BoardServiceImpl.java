package com.example.todayisdiary.domain.board.service;

import com.example.todayisdiary.domain.board.dto.BoardList;
import com.example.todayisdiary.domain.board.dto.BoardRequest;
import com.example.todayisdiary.domain.board.dto.BoardResponse;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.enums.BoardCategory;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.board.repository.BoardRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;

    @Transactional
    @Override
    public void createBoard(BoardRequest boardRequest){

        User user = userFacade.getCurrentUser();

        Board board = new Board(
                boardRequest.getTitle(),
                boardRequest.getContent(),
                boardRequest.getCategory(),
                user
        );

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void setBoard(BoardRequest boardRequest, Long id){
        Board board = boardFacade.getBoardById(id);

        board.setBord(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getCategory());
        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void delBoard(Long id){
        Board board = boardFacade.getBoardById(id);
        userMatch(board);

        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardList> boardLists(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boards = boardFacade.getBoardAllById(sort);
        List<BoardList> boardLists = new ArrayList<>();

        for(Board board : boards){
            BoardList dto = BoardList.builder()
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .date(betweenDate(board.getBoardTime()))
                    .build();
            boardLists.add(dto);
        }
        return boardLists;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardList> boardCategoryList(BoardCategory category){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Board> boards = boardFacade.getBoardAllById(sort);
        List<BoardList> boardLists = new ArrayList<>();

        for(Board board : boards){
            if(board.getCategory().equals(category)){
                BoardList dto = BoardList.builder()
                        .title(board.getTitle())
                        .category(board.getCategory())
                        .date(betweenDate(board.getBoardTime()))
                        .build();
                boardLists.add(dto);
            }
        }
        return boardLists;
    }

    // 자신의 작성한 게시글 리스트, GET, /user/title-list
    @Transactional(readOnly = true)
    @Override
    public List<BoardList> myPost() {
        User user = userFacade.getCurrentUser();
        List<Board> boards = user.getBoards();
        List<BoardList> boardLists = new ArrayList<>();

        for(Board board : boards){
            BoardList dto = BoardList.builder()
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .date(betweenDate(board.getBoardTime()))
                    .build();
            boardLists.add(dto);
        }
        return boardLists;
    }

    @Transactional(readOnly = true)
    @Override
    public BoardResponse boardDetail(Long id){
        Board board = boardFacade.getBoardById(id);
        return BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getTitle())
                .category(board.getCategory())
                .boardTime(board.getBoardTime())
                .writer(board.getUser().getNickName()).build();
    }

    private void userMatch(Board board){
        if (board.getUser().getAccountId().equals(userFacade.getCurrentUser().getAccountId()) || userFacade.getCurrentUser().getRole() == Role.ADMIN){
        }else throw new IllegalStateException("권한이 없습니다.");
    }

    // 시간, 날짜 차이 구하기
    private String betweenDate(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(dateTime.toLocalTime(), now.toLocalTime());
        if (diff.getSeconds() >= 60) {
            if (diff.toMinutes() >= 60) {
                if (diff.toHours() >= 24) {
                    Period period = Period.between(dateTime.toLocalDate(), now.toLocalDate());
                    if (period.getDays() >= 30) {
                        if (period.getMonths() >= 12) {
                            return period.getYears() + "년전";
                        } else return period.getMonths() + "달전";
                    } else return period.getDays() + "일전";
                }
            } else return diff.toMinutes() + "분전";
        } else return "방금전";
        throw new IllegalStateException("해당 하는 날짜를 찾을 수 없습니다.");
    }

}
