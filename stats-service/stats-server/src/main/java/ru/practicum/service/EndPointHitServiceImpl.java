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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
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

        if (uris != null) {
            Set<String> urisUnique = uris.stream().map(String::toLowerCase).collect(Collectors.toSet());
            log.info("Отсортированное множество URI {}", urisUnique);

            if (unique) {
                return endpointHitRepository.getStatisticUniqueIps(start, end, urisUnique);
            } else {
                return endpointHitRepository.getStatisticAllIps(start, end, urisUnique);
            }
        } else {
            log.info("Отсортированное множество URI {}", new ArrayList<String>());

            if (unique) {
                return endpointHitRepository.getAllStatisticUniqueIps(start, end);
            } else {
                return endpointHitRepository.getAllStatisticAllIps(start, end);
            }
        }
    }
}