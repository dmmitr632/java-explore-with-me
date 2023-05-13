package ru.practicum.ewm.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CommentPrivateController {

    private final CommentService commentService;

    public CommentPrivateController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/comments/{eventId}")
    public CommentDto addCommentUser(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer userId,
                                     @PathVariable Integer eventId) {
        log.info("");
        return commentService.addComment(commentDto, userId, eventId);
    }

    @PatchMapping("/users/{userId}/comments/{commentId}")
    public CommentDto editCommentUser(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer userId,
                                      @PathVariable Integer commentId) {
        log.info("");
        return commentService.editCommentUser(commentDto, userId, commentId);
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentDto> getCommentsOfUserByUser(@PathVariable Integer userId) {
        log.info("");
        return commentService.getCommentsOfUserByUser(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public void deleteCommentUser(@PathVariable Integer userId, @PathVariable Integer commentId) {
        log.info("");
        commentService.deleteCommentUser(userId, commentId);
    }


}
