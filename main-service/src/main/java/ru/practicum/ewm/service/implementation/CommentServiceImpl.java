package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }


    @Override
    public CommentDto editCommentAdmin(CommentDto commentDto, Integer id) {
        return null;
    }

    @Override
    public void deleteCommentAdmin(Integer id) {

    }

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public CommentDto editCommentUser(CommentDto commentDto, Integer userId, Integer commentId) {
        return null;
    }

    @Override
    public CommentDto getOwnCommentUser(Integer userId, Integer commentId) {
        return null;
    }

    @Override
    public List<CommentDto> getCommentsOfUserByUser(Integer userId) {
        return null;
    }

    @Override
    public String deleteCommentUser(Integer userId, Integer commentId) {
        return null;
    }

    @Override
    public CommentDto getCommentByIdPublic(Integer commentId) {
        return null;
    }

    @Override
    public List<CommentDto> getCommentsByEventIdPublic(Integer eventId, String byTime, Integer from, Integer size) {
        return null;
    }
}
