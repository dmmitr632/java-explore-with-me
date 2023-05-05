package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.enumeration.StateAction;
import ru.practicum.ewm.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.ewm.DateTimeFormatterConstant.DATE_TIME_FORMATTER;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {

    private String annotation;

    private Integer category;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMATTER)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private Boolean path;

    StateAction stateAction;
}