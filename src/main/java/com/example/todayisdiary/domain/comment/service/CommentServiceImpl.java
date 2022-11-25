package com.example.todayisdiary.domain.comment.service;

import com.example.todayisdiary.domain.comment.dto.CommentList;
import com.example.todayisdiary.domain.comment.dto.CommentRequest;
import com.example.todayisdiary.domain.comment.dto.CommentResponseList;
import com.example.todayisdiary.domain.comment.entity.Chat;
import com.example.todayisdiary.domain.comment.facade.CommentFacade;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.global.date.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentFacade commentFacade;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final CommentRepository commentRepository;
    private final DateService dateService;

    @Override
    @Transactional
    public void createChat(CommentRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Board board = boardFacade.getBoardById(id);

        Chat chat = Chat.builder()
                .comment(request.getComment())
                .writer(user.getNickName())
                .user(user)
                .board(board)
                .originChatId(0L)
                .replyChatId(0L).build();
        chat.isOrigin();
        commentRepository.save(chat);
    }

    @Override
    @Transactional
    public void createReplyChat(CommentRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Chat chat = commentFacade.getChatById(id);

        if (chat.isOriginChat()) {
            Chat chats = Chat.builder()
                    .comment(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(chat.getBoard())
                    .originChatId(chat.getId())
                    .replyChatId(chat.getId()).build();
            commentRepository.save(chats);
        } else {
            Chat chats = Chat.builder()
                    .comment(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(chat.getBoard())
                    .originChatId(chat.getOriginChatId())
                    .replyChatId(chat.getId()).build();
            commentRepository.save(chats);
        }
    }

    @Override
    @Transactional
    public void setChat(CommentRequest request, Long id) {
        Chat chat = commentFacade.getChatById(id);
        userMath(chat);

        chat.setChat(request.getComment());
        commentRepository.save(chat);
    }

    @Override
    @Transactional
    public void deleteChat(Long id) {
        Chat chat = commentFacade.getChatById(id);
        userMath(chat);

        if (chat.isOriginChat()) {
            commentRepository.delete(chat);
            List<Chat> chats = commentFacade.getChatByIdList(id);
            commentRepository.deleteAll(chats);
        } else commentRepository.delete(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseList chatList(Long id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Board board = boardFacade.getBoardById(id);
        List<Chat> chats = commentFacade.getChatAllById(sort);
        List<CommentList> commentLists = new ArrayList<>();

        for (Chat chat : chats) {
            if (board.getId().equals(chat.getBoard().getId()) && chat.isOriginChat()) {
                    CommentList dto = CommentList.builder()
                            .id(chat.getId())
                            .comment(chat.getComment())
                            .writer(chat.getWriter())
                            .date(dateService.betweenDate(chat.getChatTime()))
                            .originChatId(chat.getOriginChatId())
                            .replyChatId(chat.getReplyChatId()).build();
                    commentLists.add(dto);
            }
        }
        return new CommentResponseList(commentLists);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseList chatReplyList(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Chat chat = commentFacade.getChatById(id);
        List<Chat> chats = commentFacade.getChatAllById(sort);
        List<CommentList> commentLists = new ArrayList<>();

        for (Chat c : chats) {
            if (chat.getId().equals(c.getOriginChatId())) {
                CommentList dto = CommentList.builder()
                        .id(c.getId())
                        .comment(c.getComment())
                        .writer(c.getWriter())
                        .date(dateService.betweenDate(c.getChatTime()))
                        .originChatId(c.getOriginChatId())
                        .replyChatId(c  .getReplyChatId()).build();
                commentLists.add(dto);
            }
        }
        return new CommentResponseList(commentLists);
    }

    private void userMath(Chat chat) {
        User user = userFacade.getCurrentUser();
        if (chat.getWriter().equals(user.getNickName()) || user.getRole() == Role.ADMIN) {
            log.info("권한이 성공하였습니다.");
        } else throw new IllegalStateException("작성한 댓글이 아닙니다.");
    }

}
