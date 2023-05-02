package ru.practicum.ewm.dto;

import lombok.*;
import ru.practicum.ewm.enumeration.RequestStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Integer id;

    private String created;

    private Integer event;

    private Integer requester;

    private RequestStatus status;
}