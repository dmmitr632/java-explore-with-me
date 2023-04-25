package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT new ru.practicum.dto.ViewStatsDto(eh.app, eh.uri, count(distinct eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= ?1 AND eh.timestamp <= ?2 AND eh.uri in ?3 " +
            "GROUP BY eh.app, eh.uri, eh.ip " +
            "ORDER BY COUNT(eh) desc")
    Collection<ViewStatsDto> getStatisticUniqueIps(LocalDateTime start, LocalDateTime end, Set<String> uris);

    @Query("SELECT new ru.practicum.dto.ViewStatsDto(eh.app, eh.uri, count(eh)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= ?1 AND eh.timestamp <= ?2 AND eh.uri in ?3 " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh) desc")
    Collection<ViewStatsDto> getStatisticAllIps(LocalDateTime start, LocalDateTime end, Set<String> uris);


    @Query("SELECT new ru.practicum.dto.ViewStatsDto(eh.app, eh.uri, count(distinct eh.ip)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= ?1 AND eh.timestamp <= ?2 " +
            "GROUP BY eh.app, eh.uri, eh.ip " +
            "ORDER BY COUNT(eh) desc")
    Collection<ViewStatsDto> getAllStatisticUniqueIps(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.ViewStatsDto(eh.app, eh.uri, count(eh)) " +
            "FROM EndpointHit eh " +
            "WHERE eh.timestamp >= ?1 AND eh.timestamp <= ?2 " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY COUNT(eh) desc")
    Collection<ViewStatsDto> getAllStatisticAllIps(LocalDateTime start, LocalDateTime end);

}