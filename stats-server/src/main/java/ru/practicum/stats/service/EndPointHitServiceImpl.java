package ru.practicum.stats.service;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Transactional(readOnly = true)
public class EndPointHitServiceImpl implements EndPointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public EndPointHitServiceImpl(EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }

    @Override
    @Transactional
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        return EndpointHitMapper.toEndpointHitDto(endpointHit);
    }

    @Override
    public Collection<ViewStatsDto> getStatistic(LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                                 boolean unique) {

        String app = "main-service";

        if (uris != null) {
            Map<String, Integer> endpointHitsMap = new HashMap<>();
            Set<String> urisUnique = uris.stream().map(String::toLowerCase).collect(Collectors.toSet());
            SortedSet<String> urisUniqueSorted = Collections.synchronizedSortedSet(new TreeSet<>(urisUnique));
            urisUniqueSorted.forEach(uri -> endpointHitsMap.put(uri, 0));
            log.info("Отсортированное множество URI urisUniqueSorted {}", urisUniqueSorted);

            if (unique) {
                Collection<EndpointHit> endpointHits = endpointHitRepository.getStatistic(urisUniqueSorted, start, end);
                Map<String, Set<String>> uniqueIpsPerUriMap = new HashMap<>();
                urisUniqueSorted.forEach(uri -> uniqueIpsPerUriMap.put(uri, new HashSet<>()));


                for (EndpointHit endpointHit : endpointHits) {
                    String uri = endpointHit.getUri();
                    if (!(uniqueIpsPerUriMap.get(uri).contains(endpointHit.getIp()))) {
                        uniqueIpsPerUriMap.get(uri).add(endpointHit.getIp());
                        endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1);
                    }
                }
                log.info("HashMap, ключ - URI, значение  - множество уникальных ip {}", uniqueIpsPerUriMap);
                log.info("URI и количество посещений endpointHitsMap {}", endpointHitsMap);
            } else {
                endpointHitRepository.getUrisOnly(urisUniqueSorted, start, end)
                        .forEach(uri -> endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1));
                log.info("URI и количество посещений endpointHitsMap {}", endpointHitsMap);
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