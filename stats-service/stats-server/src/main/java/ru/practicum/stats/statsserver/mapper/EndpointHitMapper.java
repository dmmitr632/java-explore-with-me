package ru.practicum.stats.statsserver.mapper;

import ru.practicum.stats.statsserver.model.EndpointHit;
import ru.practicum.stats.statsdto.dto.EndpointHitDto;
import ru.practicum.stats.statsdto.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {
    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder().id(endpointHitDto.getId()).app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri()).ip(endpointHitDto.getIp()).timestamp(
                        LocalDateTime.parse(endpointHitDto.getTimestamp(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder().id(endpointHit.getId()).app(endpointHit.getApp())
                .uri(endpointHit.getUri()).ip(endpointHit.getIp()).timestamp(
                        endpointHit.getTimestamp().toString()).build();
    }

    public static ViewStatsDto toViewStatsDto(EndpointHit endpointHit) {
        return ViewStatsDto.builder().app(endpointHit.getApp()).uri(endpointHit.getUri()).build();
    }
}
