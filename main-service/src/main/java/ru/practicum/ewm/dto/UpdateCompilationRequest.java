package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Getter
@Setter
@ToString
public class UpdateCompilationRequest {
    private List<Integer> events;

    private Boolean pinned;

    private String title;
}
