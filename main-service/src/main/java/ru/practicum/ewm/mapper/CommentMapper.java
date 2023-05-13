package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.model.Comment;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto(comment.getId(), UserMapper.toUserShortDto(comment.getUser()),
                comment.getEvent().getId(), comment.getText(), comment.getCreatedOn());
        if (comment.getUpdatedOn() != null) {
            commentDto.setUpdatedOn(comment.getUpdatedOn());
        }
        return commentDto;

    }
}
