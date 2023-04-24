package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

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

        String app = "ewm-main-service";

        if (uris != null) {
            Map<String, Integer> endpointHitsMap = new HashMap<>();
            Set<String> urisUnique = uris.stream().map(String::toLowerCase).collect(Collectors.toSet());

            urisUnique.forEach(uri -> endpointHitsMap.put(uri, 0));
            log.info("Отсортированное множество URI urisUnique {}", urisUnique);

            if (unique) {
                Collection<EndpointHit> endpointHits = endpointHitRepository.getStatistic(urisUnique, start, end);
                log.info("endpointHits {}", endpointHits);
                Map<String, Set<String>> uniqueIpsPerUriMap = new HashMap<>();
                urisUnique.forEach(uri -> uniqueIpsPerUriMap.put(uri, new HashSet<>()));

                endpointHits.forEach(endpointHit -> {
                    String uri = endpointHit.getUri();
                    uniqueIpsPerUriMap.get(uri).add(endpointHit.getIp());
                    endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1);
                });

//                for (EndpointHit endpointHit : endpointHits) {
//                    String uri = endpointHit.getUri();
//                    if (!(uniqueIpsPerUriMap.get(uri).contains(endpointHit.getIp()))) {
//                        uniqueIpsPerUriMap.get(uri).add(endpointHit.getIp());
//                        endpointHitsMap.put(uri, endpointHitsMap.get(uri) + 1);
//                    }
//                }
                
                log.info("HashMap, ключ - URI, значение  - множество уникальных ip {}", uniqueIpsPerUriMap);
                log.info("URI и количество посещений endpointHitsMap {}", endpointHitsMap);
            } else {
                endpointHitRepository.getUrisOnly(urisUnique, start, end)
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