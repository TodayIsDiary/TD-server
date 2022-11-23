package com.example.todayisdiary.domain.chat.service;

import com.example.todayisdiary.domain.chat.dto.ChatList;
import com.example.todayisdiary.domain.chat.dto.ChatRequest;
import com.example.todayisdiary.domain.chat.dto.ChatResponseList;
import com.example.todayisdiary.domain.chat.entity.Chat;
import com.example.todayisdiary.domain.chat.facade.ChatFacade;
import com.example.todayisdiary.domain.chat.repository.ChatRepository;
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
public class ChatServiceImpl implements ChatService {
    private final ChatFacade chatFacade;
    private final UserFacade userFacade;
    private final BoardFacade boardFacade;
    private final ChatRepository chatRepository;
    private final DateService dateService;

    @Override
    @Transactional
    public void createChat(ChatRequest request, Long id) {
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
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void createReplyChat(ChatRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Chat chat = chatFacade.getChatById(id);

        if (chat.isOriginChat()) {
            Chat chats = Chat.builder()
                    .comment(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(chat.getBoard())
                    .originChatId(chat.getId())
                    .replyChatId(chat.getId()).build();
            chatRepository.save(chats);
        } else {
            Chat chats = Chat.builder()
                    .comment(request.getComment())
                    .writer(user.getNickName())
                    .user(user)
                    .board(chat.getBoard())
                    .originChatId(chat.getOriginChatId())
                    .replyChatId(chat.getId()).build();
            chatRepository.save(chats);
        }
    }

    @Override
    @Transactional
    public void setChat(ChatRequest request, Long id) {
        Chat chat = chatFacade.getChatById(id);
        userMath(chat);

        chat.setChat(request.getComment());
        chatRepository.save(chat);
    }

    @Override
    @Transactional
    public void deleteChat(Long id) {
        Chat chat = chatFacade.getChatById(id);
        userMath(chat);

        if (chat.isOriginChat()) {
            chatRepository.delete(chat);
            List<Chat> chats = chatFacade.getChatByIdList(id);
            chatRepository.deleteAll(chats);
        } else chatRepository.delete(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public ChatResponseList chatList(Long id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Board board = boardFacade.getBoardById(id);
        List<Chat> chats = chatFacade.getChatAllById(sort);
        List<ChatList> chatLists = new ArrayList<>();

        for (Chat chat : chats) {
            if (board.getId().equals(chat.getBoard().getId()) && chat.isOriginChat()) {
                    ChatList dto = ChatList.builder()
                            .id(chat.getId())
                            .comment(chat.getComment())
                            .writer(chat.getWriter())
                            .date(dateService.betweenDate(chat.getChatTime()))
                            .originChatId(chat.getOriginChatId())
                            .replyChatId(chat.getReplyChatId()).build();
                    chatLists.add(dto);
            }
        }
        return new ChatResponseList(chatLists);
    }

    @Override
    @Transactional(readOnly = true)
    public ChatResponseList chatReplyList(Long id) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Chat chat = chatFacade.getChatById(id);
        List<Chat> chats = chatFacade.getChatAllById(sort);
        List<ChatList> chatLists = new ArrayList<>();

        for (Chat c : chats) {
            if (chat.getId().equals(c.getOriginChatId())) {
                ChatList dto = ChatList.builder()
                        .id(c.getId())
                        .comment(c.getComment())
                        .writer(c.getWriter())
                        .date(dateService.betweenDate(c.getChatTime()))
                        .originChatId(c.getOriginChatId())
                        .replyChatId(c  .getReplyChatId()).build();
                chatLists.add(dto);
            }
        }
        return new ChatResponseList(chatLists);
    }

    private void userMath(Chat chat) {
        User user = userFacade.getCurrentUser();
        if (chat.getWriter().equals(user.getNickName()) || user.getRole() == Role.ADMIN) {
            log.info("권한이 성공하였습니다.");
        } else throw new IllegalStateException("작성한 댓글이 아닙니다.");
    }

}
