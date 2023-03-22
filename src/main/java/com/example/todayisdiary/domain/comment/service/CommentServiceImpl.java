package com.example.todayisdiary.domain.comment.service;

import com.example.todayisdiary.domain.comment.dto.CommentList;
import com.example.todayisdiary.domain.comment.dto.CommentRequest;
import com.example.todayisdiary.domain.comment.dto.CommentResponseList;
import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.comment.facade.CommentFacade;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.domain.board.entity.Board;
import com.example.todayisdiary.domain.board.facade.BoardFacade;
import com.example.todayisdiary.domain.like.entity.CommentLove;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import com.example.todayisdiary.global.date.DateService;
import com.example.todayisdiary.global.error.ErrorCode;
import com.example.todayisdiary.global.error.exception.CustomException;
import com.example.todayisdiary.global.s3.facade.S3Facade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final S3Facade s3Facade;
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

        Comment comment = Comment.builder()
                .comments(request.getComment())
                .writer(user.getNickName())
                .user(user)
                .board(board)
                .originChatId(0L)
                .replyChatId(0L)
                .imageUrl(user.getImageUrl()).build();
        comment.isOrigin();
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void createReplyChat(CommentRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Comment comment = commentFacade.getChatById(id);

        if (comment.isOriginChat()) {
            Comment comments = Comment.builder()
                    .comments(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(comment.getBoard())
                    .originChatId(comment.getId())
                    .replyChatId(comment.getId())
                    .imageUrl(user.getImageUrl()).build();
            commentRepository.save(comments);
        } else {
            Comment comments = Comment.builder()
                    .comments(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(comment.getBoard())
                    .originChatId(comment.getOriginChatId())
                    .replyChatId(comment.getId())
                    .imageUrl(user.getImageUrl()).build();
            commentRepository.save(comments);
        }
    }

    @Override
    @Transactional
    public void setChat(CommentRequest request, Long id) {
        Comment comment = commentFacade.getChatById(id);
        userMath(comment);

        comment.setChat(request.getComment());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteChat(Long id) {
        Comment comment = commentFacade.getChatById(id);
        userMath(comment);

        if (comment.isOriginChat()) {
            commentRepository.delete(comment);
            List<Comment> comments = commentFacade.getChatByOriginId(id);
            commentRepository.deleteAll(comments);
        } else commentRepository.delete(comment);
    }
    @Override
    @Transactional(readOnly = true)
        public CommentResponseList chatList(Long id) {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Board board = boardFacade.getBoardById(id);
            List<Comment> comments = commentFacade.getChatAllById(sort);

            List<CommentList> commentLists = comments.stream()
                .filter(comment -> board.getId().equals(comment.getBoard().getId()) && comment.isOriginChat())
                .map(comment -> CommentList.builder()
                        .id(comment.getId())
                        .comment(comment.getComments())
                        .writer(comment.getWriter())
                        .date(dateService.betweenDate(comment.getChatTime()))
                        .heart(comment.getHeart())
                        .isLiked(writerLike(comment))
                        .originChatId(comment.getOriginChatId())
                        .replyChatId(comment.getReplyChatId())
                        .userId(comment.getUser().getId())
                        .imageUrl(s3Facade.getUrl(comment.getImageUrl())).build())
                .collect(Collectors.toList());

            return new CommentResponseList(commentLists);
        }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseList chatReplyList(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Comment comment = commentFacade.getChatById(id);
        List<Comment> comments = commentFacade.getChatAllById(sort);

        List<CommentList> commentLists = comments.stream()
                .filter(c -> comment.getId().equals(c.getOriginChatId()))
                .map(c -> CommentList.builder()
                        .id(c.getId())
                        .comment(c.getComments())
                        .writer(c.getWriter())
                        .date(dateService.betweenDate(c.getChatTime()))
                        .originChatId(c.getOriginChatId())
                        .heart(c.getHeart())
                        .isLiked(writerLike(c))
                        .replyChatId(c.getReplyChatId())
                        .userId(c.getUser().getId())
                        .imageUrl(s3Facade.getUrl(c.getImageUrl())).build())
                .collect(Collectors.toList());

        return new CommentResponseList(commentLists);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseList chatHeartList(Long id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "heart");
        Board board = boardFacade.getBoardById(id);
        List<Comment> comments = commentFacade.getChatAllById(sort);

        List<CommentList> commentLists = comments.stream()
                .filter(c -> board.getId().equals(c.getBoard().getId()) && c.isOriginChat())
                .map(c -> CommentList.builder()
                        .id(c.getId())
                        .comment(c.getComments())
                        .writer(c.getWriter())
                        .date(dateService.betweenDate(c.getChatTime()))
                        .originChatId(c.getOriginChatId())
                        .heart(c.getHeart())
                        .isLiked(writerLike(c))
                        .replyChatId(c.getReplyChatId())
                        .userId(c.getUser().getId())
                        .imageUrl(s3Facade.getUrl(c.getImageUrl())).build())
                .collect(Collectors.toList());

        return new CommentResponseList(commentLists);
    }

    private void userMath(Comment comment) {
        User user = userFacade.getCurrentUser();
        if (comment.getWriter().equals(user.getNickName()) || user.getRole() == Role.ROLE_ADMIN) {
            ;
        } else throw new CustomException(ErrorCode.USER_MISS_MATCHED);
    }

    private boolean writerLike(Comment comment){
        User user = userFacade.getCurrentUser();
        List<CommentLove> commentLoves = comment.getCommentLoves();

        for(CommentLove commentLove : commentLoves){
            if(commentLove.getUser().getAccountId().equals(user.getAccountId())){
                return true;
            }
        }
        return false;
    }

}
