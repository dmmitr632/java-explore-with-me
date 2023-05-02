package ru.practicum.ewm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {

    private Integer id;

    private String title;

    private Boolean pinned;

    private List<EventShortDto> events;
}
