package ru.practicum.ewm.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ParticipationRequestPrivateController {
    private final ParticipationRequestService participationRequestService;

    public ParticipationRequestPrivateController(ParticipationRequestService participationRequestService) {
        this.participationRequestService = participationRequestService;
    }

    @GetMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUserParticipationRequests(@PathVariable Integer userId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение пользователем списка заявок на события пользователя с userId {}",
                userId);
        log.info("========================================");
        log.info("                                                                           ");
        return new ArrayList<>(participationRequestService.getUserParticipationRequests(userId));
    }

    @PostMapping(path = "/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addUserParticipationRequest(@PathVariable Integer userId,
                                                               @RequestParam Integer eventId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Добавление пользователем заявки на событие, userId {}, eventId {}",
                userId, eventId);
        log.info("========================================");
        log.info("                                                                           ");
        return participationRequestService.addUserParticipationRequest(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelUserParticipationRequest(@PathVariable Integer userId,
                                                                  @PathVariable Integer requestId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Отмена пользователем заявки на событие, userId {}, requestId {}",
                userId, requestId);
        log.info("========================================");
        log.info("                                                                           ");
        return participationRequestService.cancelUserParticipationRequest(userId, requestId);
    }
}
