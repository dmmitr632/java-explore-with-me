package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {
    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder().id(endpointHitDto.getId()).app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri()).ip(endpointHitDto.getIp()).timestamp(
                        LocalDateTime.parse(endpointHitDto.getTimestamp(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
    }

    public static ViewStatsDto toViewStatsDto(EndpointHit endpointHit) {
        return ViewStatsDto.builder().app(endpointHit.getApp()).uri(endpointHit.getUri()).build();
    }
}
