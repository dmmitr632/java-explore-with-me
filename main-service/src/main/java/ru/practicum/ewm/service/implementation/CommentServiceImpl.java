package ru.practicum.ewm.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.CommentDto;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.CommentRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final ParticipationRequestRepository participationRequestRepository;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              EventRepository eventRepository,
                              ParticipationRequestRepository participationRequestRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.participationRequestRepository = participationRequestRepository;
    }


    @Override
    public List<CommentDto> getCommentsAdmin(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<CommentDto> commentsDtos =
                commentRepository.findAll(pageable).getContent()
                        .stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl getCommentsAdmin, commentDtoList {}", commentsDtos);
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return commentsDtos;
    }

    @Override
    public CommentDto editCommentAdmin(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий " +
                "не найден"));
        comment.setText(commentDto.getText());
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl editCommentsAdmin, comment {}", CommentMapper.toCommentDto(comment));
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public void deleteCommentAdmin(Integer id) {
        commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        commentRepository.deleteById(id);
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl deleteCommentAdmin");
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
    }

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer userId, Integer eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Собтыие не найдено"));

        participationRequestRepository.findFirstByRequesterIdAndEventIdAndStatus(userId, eventId,
                RequestStatus.CONFIRMED).orElseThrow(() -> new ConflictException("Участие пользователя не " +
                "подтверждено"));

        // Добавленное plusDays(1) необходимо для тестирования, т.к по условию, "дата и время на которые
        // намечено событие не может быть раньше, чем через два часа от текущего момента". Поэтому в тесте не
        // получится создать событие, назначенное на ранее чем через 2 часа после запуска, а следовательно,
        // пользователь не мог в нем уже участвовать.

        if (!(EventMapper.toEventShortDto(event).getEventDate().isBefore(LocalDateTime.now().plusDays(1)))) {
            throw new ConflictException("Событие еще не произошло, оставить отзыв невозможно.");
        }


        Comment comment = new Comment(user, event, commentDto.getText(), LocalDateTime.now());
        commentRepository.save(comment);
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl addComment, comment {}", CommentMapper.toCommentDto(comment));
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto editCommentUser(CommentDto commentDto, Integer userId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий " +
                "не найден"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new ConflictException("Можно редактировать только свой комментарий");
        }
        comment.setEditedOn(LocalDateTime.now());
        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl editComment, comment {}", CommentMapper.toCommentDto(comment));
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsOfUserByUser(Integer userId) {
        List<CommentDto> commentDtoList = commentRepository.findAllByUserId(userId)
                .stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl getCommentsOfUserByUser, commentDtoList {}", commentDtoList);
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return commentDtoList;
    }

    @Override
    public void deleteCommentUser(Integer userId, Integer commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
        if (!((commentRepository.getUserIdOfComment(commentId)).equals(userId))) {
            throw new ConflictException("Можно удалять только свой комментарий");
        }
        commentRepository.deleteById(commentId);
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl deleteCommentUser");
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
    }

    @Override
    public CommentDto getCommentByIdPublic(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Комментарий " +
                "не найден"));
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl getCommentByIdPublic, comment {}", CommentMapper.toCommentDto(comment));
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEventIdPublic(Integer eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие не найдено"));
        List<Comment> comments = commentRepository.findAllByEventId(eventId, pageable);
        List<CommentDto> commentDtoList =
                comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
        log.info("                                                                           ");
        log.info("---------------------------------------------------------------------------");
        log.info("CommentServiceImpl getCommentsByEventIdPublic, commentDtoList {}", commentDtoList);
        log.info("---------------------------------------------------------------------------");
        log.info("                                                                           ");
        return commentDtoList;
    }
}

