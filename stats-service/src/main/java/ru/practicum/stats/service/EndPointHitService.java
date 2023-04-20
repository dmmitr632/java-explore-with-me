package ru.practicum.stats.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EndPointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public EndPointHitService(EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }

    @Transactional public void addHit(EndpointHitDto endpointHitDto) {
        endpointHitRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
    }

    public Collection<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                                 boolean unique) {
        List<EndpointHit> endpointHits= endpointHitRepository.findDistinctByTimestampBetween(start, end);
        List<ViewStatsDto> viewStatsDtos = new ArrayList<>();
        for (EndpointHit endpointHit: endpointHits) {
            viewStatsDtos.add(EndpointHitMapper.toViewStatsDto(endpointHit));
        }
        return viewStatsDtos;
    }
}