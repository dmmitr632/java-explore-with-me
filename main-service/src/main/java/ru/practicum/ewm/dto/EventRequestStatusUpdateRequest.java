package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.enumeration.RequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {

    List<Integer> requestIds;

    RequestStatus status;
}
