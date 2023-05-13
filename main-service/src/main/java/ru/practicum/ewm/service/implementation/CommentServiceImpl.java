package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ArrayList<CommentDto> getCommentsAdmin(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Comment> comments = commentRepository.findAll(pageable).getContent();
        ArrayList<CommentDto> commentDtoList = new ArrayList<>();
        comments.forEach(c -> commentDtoList.add(CommentMapper.toCommentDto(c)));
        return commentDtoList;
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
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(""));
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
    public List<CommentDto> getCommentsOfUserByUser(Integer userId) {
        return commentRepository.findAllByUserId(userId)
                .stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentUser(Integer userId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(""));
        if (!((commentRepository.findUserId(comment).getId()).equals(userId))) {
            throw new BadRequestException("Можно удалять только свой комментарий");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto getCommentByIdPublic(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(""));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEventIdPublic(Integer eventId, String byTime, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(""));
        List<Comment> comments = commentRepository.findAllByEventId(eventId, pageable);
        if (Objects.equals(byTime, "asc")) {
            comments.sort(Comparator.comparing(Comment::getCreatedOn));
        } else if (Objects.equals(byTime, "desc")) {
            comments.sort(Comparator.comparing(Comment::getCreatedOn).reversed());
        }
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }
}

