package ru.practicum.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor

public class EndpointHitDto {

    private Integer id;

    private String app;

    private String uri;

    private String ip;

    private String timestamp;

}