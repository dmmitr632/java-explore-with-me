package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.ParticipationRequest;

import java.time.format.DateTimeFormatter;

public class ParticipationRequestMapper {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto
                .builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated())
                .event(participationRequest.getEvent().getId())
                .requester(participationRequest.getRequester().getId())
                .status(participationRequest.getStatus())
                .build();
    }

    public static ParticipationRequest toParticipation(ParticipationRequestDto participationRequestDto) {
        return ParticipationRequest
                .builder()
                .id(participationRequestDto.getId())
                .created(participationRequestDto.getCreated())
                .status(participationRequestDto.getStatus())
                .build();
    }


}
