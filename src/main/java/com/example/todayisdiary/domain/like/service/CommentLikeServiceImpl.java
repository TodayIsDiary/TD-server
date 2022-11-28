package com.example.todayisdiary.domain.like.service;

import com.example.todayisdiary.domain.comment.entity.Comment;
import com.example.todayisdiary.domain.comment.facade.CommentFacade;
import com.example.todayisdiary.domain.comment.repository.CommentRepository;
import com.example.todayisdiary.domain.like.entity.CommentLove;
import com.example.todayisdiary.domain.like.repository.CommentLikeRepository;
import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService{
    private final UserFacade userFacade;
    private final CommentFacade commentFacade;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    @Transactional
    public void commentLike(Long commentId){
        User user = userFacade.getCurrentUser();
        Comment comment = commentFacade.getChatById(commentId);

        comment.likes();

        CommentLove commentLove = CommentLove.builder()
                .user(user)
                .comment(comment).build();

        comment.addHeart();

        commentRepository.save(comment);
        commentLikeRepository.save(commentLove);
    }

    @Override
    @Transactional
    public void commentLikeCancel(Long commentId){
        User user = userFacade.getCurrentUser();
        Comment comment = commentFacade.getChatById(commentId);
        List<CommentLove> like = comment.getCommentLoves();

        for(CommentLove c : like){
            if(c.getUser().equals(user)){
                comment.unlikes();

                comment.deleteHeart();
                commentRepository.save(comment);
                commentLikeRepository.delete(c);
            }
        }
    }
}
