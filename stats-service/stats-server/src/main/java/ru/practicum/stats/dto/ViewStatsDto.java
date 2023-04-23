package ru.practicum.stats.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString

public class ViewStatsDto {

    private String app;

    private String uri;

    private Integer hits;

}