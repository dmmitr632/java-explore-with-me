package ru.practicum.stats.statsdto.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString

public class EndpointHitDto {
    public EndpointHitDto(String app, String uri, String ip, String timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }

    public EndpointHitDto() {
    }

    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}