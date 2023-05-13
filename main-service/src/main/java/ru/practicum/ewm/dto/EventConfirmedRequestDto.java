package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventConfirmedRequestDto {
    private Integer eventId;
    private Long confirmedRequestsAmount;

    public EventConfirmedRequestDto(Integer eventId, Long confirmedRequestsAmount) {
        this.eventId = eventId;
        this.confirmedRequestsAmount = confirmedRequestsAmount;
    }
}
