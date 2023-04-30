package ru.practicum.ewm.dto;

import lombok.Data;

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
}