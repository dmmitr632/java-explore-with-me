package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.enumeration.EventState;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class EventFullDto {

    private String annotation;

    private CategoryDto category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer id;

    private UserShortDto initiator;

    private Float lat;

    private Float lon;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private LocalDateTime publishedOn;

    private EventState eventState;

    private String title;

    private Integer views;

    public EventFullDto(String annotation, CategoryDto category,
                        LocalDateTime createdOn, String description, LocalDateTime eventDate,
                        Integer id, UserShortDto initiator, Float lat, Float lon,
                        Boolean paid, Integer participantLimit,
                        LocalDateTime publishedOn, String title, Boolean requestModeration,
                        Integer views) {
        this.annotation = annotation;
        this.category = category;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.id = id;
        this.initiator = initiator;
        this.lat = lat;
        this.lon = lon;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.title = title;
        this.requestModeration = requestModeration;
        this.views = views;
    }
}