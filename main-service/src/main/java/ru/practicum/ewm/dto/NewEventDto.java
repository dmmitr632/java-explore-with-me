package ru.practicum.ewm.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@Setter
@Getter
@ToString
public class NewEventDto {
    @NotEmpty
    private String annotation;
    private Integer category;
    @NotEmpty
    private String description;
    @NotEmpty
    private String eventDate;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    @Value("0")
    private Integer participantLimit;
    @Value("true")
    private Boolean requestModeration;
    @NotEmpty
    private String title;
}