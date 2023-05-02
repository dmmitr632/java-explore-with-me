package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.ToString;
import ru.practicum.ewm.enumeration.StateAction;
import ru.practicum.ewm.model.Location;

@Data
@ToString
public class UpdateEventUserRequest {

    private String annotation;

    private Integer category;

    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    private String title;


}