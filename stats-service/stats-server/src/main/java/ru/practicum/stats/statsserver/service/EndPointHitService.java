package ru.practicum.stats.statsserver.service;

import ru.practicum.stats.statsdto.dto.EndpointHitDto;
import ru.practicum.stats.statsdto.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EndPointHitService {
    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                          boolean unique);
}
