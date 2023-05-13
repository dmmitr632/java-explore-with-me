package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.service.CommentService;

import java.util.List;

@RestController
@Slf4j
public class CommentPublicController {

    private final CommentService commentService;

    public CommentPublicController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentByIdPublic(@PathVariable Integer commentId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("CommentPublicController getCommentByIdPublic, commentId {}", commentId);
        log.info("========================================");
        log.info("                                                                           ");
        return commentService.getCommentByIdPublic(commentId);
    }

    @GetMapping("/comments/events/{eventId}")
    public List<CommentDto> getCommentsByEventIdPublic(@PathVariable Integer eventId,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("CommentPublicController getCommentsByEventIdPublic, eventId {}", eventId);
        log.info("========================================");
        log.info("                                                                           ");
        return commentService.getCommentsByEventIdPublic(eventId, from, size);
    }
}
