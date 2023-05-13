package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.CommentService;

import java.time.LocalDateTime;
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
    public CommentDto editCommentAdmin(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(""));
        comment.setText(commentDto.getText());
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public void deleteCommentAdmin(Integer id) {
        commentRepository.findById(id).orElseThrow(() -> new NotFoundException(""));
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer userId, Integer eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(""));
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new NotFoundException(""));
        Comment comment = new Comment(user, event, commentDto.getText(), LocalDateTime.now());
        commentRepository.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto editCommentUser(CommentDto commentDto, Integer userId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(""));
        if (!comment.getUser().getId().equals(userId)) {
            throw new BadRequestException("Можно редактировать только свой комментарий");
        }
        comment.setUpdatedOn(LocalDateTime.now());
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return CommentMapper.toCommentDto(comment);
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
