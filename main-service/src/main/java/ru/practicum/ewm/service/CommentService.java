package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsAdmin(Integer from, Integer size);

    CommentDto editCommentAdmin(CommentDto commentDto, Integer id);

    void deleteCommentAdmin(Integer id);

    CommentDto addComment(CommentDto commentDto, Integer userId, Integer eventId);

    CommentDto editCommentUser(CommentDto commentDto, Integer userId, Integer commentId);

    List<CommentDto> getCommentsOfUserByUser(Integer userId);

    void deleteCommentUser(Integer userId, Integer commentId);

    CommentDto getCommentByIdPublic(Integer commentId);

    List<CommentDto> getCommentsByEventIdPublic(Integer eventId, Integer from, Integer size);
}
