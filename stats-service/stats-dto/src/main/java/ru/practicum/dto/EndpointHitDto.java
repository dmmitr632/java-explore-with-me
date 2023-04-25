package ru.practicum.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString

public class EndpointHitDto {
    public EndpointHitDto() {
    }

    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}