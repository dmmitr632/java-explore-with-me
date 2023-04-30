package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UpdateCompilationDto {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}
