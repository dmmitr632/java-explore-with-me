package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {
    List<EndpointHit> findDistinctByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<EndpointHit> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);

}