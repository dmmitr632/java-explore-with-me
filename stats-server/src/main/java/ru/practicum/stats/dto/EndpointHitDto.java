package ru.practicum.stats.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class EndpointHitDto {

    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}