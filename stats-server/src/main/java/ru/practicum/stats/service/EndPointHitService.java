package ru.practicum.stats.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.mapper.EndpointHitMapper;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EndPointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public EndPointHitService(EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }

    @Transactional
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

    public Collection<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                                 boolean unique) {

        String app = "main-service";

        if (uris != null) {
            Map<String, Integer> endpointHitsMap = new HashMap<>();
            Set<String> urisUnique = uris.stream().map(String::toLowerCase).collect(Collectors.toSet());
            urisUnique.forEach(uri -> endpointHitsMap.put(uri, 0));

            if (unique) {
                Collection<EndpointHit> endpointHits = endpointHitRepository.getStatistic(urisUnique, start, end);
                Map<String, Set<String>> uniqueIpsPerUriMap = new HashMap<>();
                urisUnique.forEach(uri -> uniqueIpsPerUriMap.put(uri, new HashSet<>()));

                for (EndpointHit endpointHit : endpointHits) {
                    String uri = endpointHit.getUri();
                    if (!(uniqueIpsPerUriMap.get(uri).contains(endpointHit.getIp()))) {
                        uniqueIpsPerUriMap.get(uri).add(endpointHit.getIp());
                        endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1);
                    }
                }

            } else {
                endpointHitRepository.getUrisOnly(urisUnique, start, end)
                        .forEach(uri -> endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1));
            }
            return uris.stream()
                    .map(uri -> ViewStatsDto.builder().app(app).uri(uri).hits(endpointHitsMap.get(uri)).build())
                    .sorted(Comparator.comparingInt(ViewStatsDto::getHits).reversed())
                    .collect(Collectors.toList());
        } else {
            return endpointHitRepository.getEndpointHitsByTimestampBetween(start, end).stream()
                    .map(EndpointHitMapper::toViewStatsDto).collect(
                            Collectors.toList());

        }
    }
}