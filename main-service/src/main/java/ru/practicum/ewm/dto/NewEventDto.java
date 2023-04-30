package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "Для создания события необходимо указать дату его проведения")
    private String eventDate;
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
    @NotNull
    private Boolean paid;
    @Value("0")
    private Integer participantLimit;
    @Value("true")
    private Boolean requestModeration;
    @NotEmpty
    private String title;
}