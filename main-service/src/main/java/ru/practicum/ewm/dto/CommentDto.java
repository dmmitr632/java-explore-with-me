package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.ewm.DateTimeFormatterConstant.DATE_TIME_FORMATTER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Integer id;

    @NotNull
    private UserShortDto user;

    @NotNull
    private Integer event;

    @NotEmpty
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMATTER)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMATTER)
    private LocalDateTime updatedOn;

    public CommentDto(Integer id, UserShortDto user, Integer event, String text, LocalDateTime createdOn) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.text = text;
        this.createdOn = createdOn;
    }
}