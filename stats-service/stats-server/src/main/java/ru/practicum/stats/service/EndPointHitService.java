package ru.practicum.stats.service;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EndPointHitService {
    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                          boolean unique);
}
