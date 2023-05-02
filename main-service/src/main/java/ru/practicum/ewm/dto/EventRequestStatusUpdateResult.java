package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventRequestStatusUpdateResult {

    List<ParticipationRequestDto> confirmedRequests;

    List<ParticipationRequestDto> rejectedRequests;
}