package ru.practicum.ewm.dto;

import lombok.Data;
import ru.practicum.ewm.enumeration.StateAction;

@Data
public class UpdateEventAdminRequest {

    private String annotation;

    private Integer category;

    private String description;

    private String eventDate;

    private Float lat;

    private Float lon;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private Boolean path;

    StateAction stateAction;
}