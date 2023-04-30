package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.enumeration.EventState;

import java.time.LocalDateTime;

@Data
public class EventDto {

    private Integer id;

    private String title;

    private String annotation;

    private CategoryDto category;

    private Boolean paid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private String description;

    private Integer participantLimit;

    private EventState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private Float lat;

    private Float lon;


    private Boolean requestModeration;

    public EventDto(Integer id, String title, String annotation, CategoryDto category,
                    Boolean paid, LocalDateTime eventDate, UserShortDto initiator,
                    String description, Integer participantLimit,
                    LocalDateTime createdOn, Float lat, Float lon, Boolean requestModeration) {
        this.id = id;
        this.title = title;
        this.annotation = annotation;
        this.category = category;
        this.paid = paid;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.description = description;
        this.participantLimit = participantLimit;
        this.createdOn = createdOn;
        this.lat = lat;
        this.lon = lon;
        this.requestModeration = requestModeration;
    }
}