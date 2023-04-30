package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@NoArgsConstructor
@ToString
public class NewCompilationDto {
    @NotEmpty
    private String title;
    @Value("false")
    private Boolean pinned;
    private Collection<Integer> events;
}