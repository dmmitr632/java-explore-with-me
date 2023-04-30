package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventShortDto {

    private Integer id;

    private String title;

    private String annotation;

    private CategoryDto category;

    private Boolean paid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Integer confirmedRequests;

    private Integer views;

    public EventShortDto(Integer id, String title, String annotation, CategoryDto category,
                         Boolean paid, LocalDateTime eventDate, UserShortDto initiator) {
        this.id = id;
        this.title = title;
        this.annotation = annotation;
        this.category = category;
        this.paid = paid;
        this.eventDate = eventDate;
        this.initiator = initiator;
    }
}