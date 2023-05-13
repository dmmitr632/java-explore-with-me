package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CommentAdminController {
    private final CommentService commentService;

    public CommentAdminController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/admin/comments/")
    public List<CommentDto> getCommentsAdmin(@RequestParam(required = false, defaultValue = "0") Integer from,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return commentService.getCommentsAdmin(from, size);
    }

    @PatchMapping("/admin/comments/{commentId}")
    public CommentDto editCommentAdmin(@Valid @RequestBody CommentDto commentDto,
                                       @PathVariable Integer commentId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("CommentAdminController editComment, commentId {}", commentId);
        log.info("========================================");
        log.info("                                                                           ");
        return commentService.editCommentAdmin(commentDto, commentId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/comments/{commentId}")
    public void deleteCommentAdmin(@PathVariable Integer commentId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("CommentAdminController editComment, commentId {}", commentId);
        log.info("========================================");
        log.info("                                                                           ");
        commentService.deleteCommentAdmin(commentId);
    }
}