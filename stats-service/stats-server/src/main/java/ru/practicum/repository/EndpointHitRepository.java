package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("SELECT eh FROM EndpointHit eh " +
            "WHERE eh.uri IN ?1 AND ?2 <= eh.timestamp AND eh.timestamp <= ?3 ")
    Collection<EndpointHit> getStatistic(Collection<String> uris, LocalDateTime start, LocalDateTime end);


//    @Query("SELECT new EndpointHit(eh.id, eh.app, eh.uri, eh.ip, eh.timestamp) FROM EndpointHit eh " +
//            "WHERE eh.uri IN ?1 AND ?2 <= eh.timestamp AND eh.timestamp <= ?3 " +
//            "GROUP BY eh.id, eh.app, eh.uri, eh.ip, eh.timestamp " +
//            "ORDER BY COUNT(DISTINCT eh.ip) DESC")
//    Collection<EndpointHit> getStatistic(Collection<String> uris, LocalDateTime start, LocalDateTime end);

//endpointHits[EndpointHit(id=1, app=ewm-main-service, uri=/events, ip=121.0.0.1, timestamp=2023-04-24T19:52:21),
// EndpointHit(id=3, app=ewm-main-service, uri=/events, ip=121.0.0.1, timestamp=2023-04-24T19:52:22),
// EndpointHit(id=4, app=ewm-main-service, uri=/events, ip=121.0.0.1, timestamp=2023-04-24T19:52:22),
// EndpointHit(id=5, app=ewm-main-service, uri=/events, ip=121.0.0.1, timestamp=2023-04-24T19:52:22)]
//

    @Query("SELECT eh.uri FROM EndpointHit eh " +
            "WHERE eh.uri IN ?1 AND ?2 <= eh.timestamp AND eh.timestamp <= ?3")
    Collection<String> getUrisOnly(Collection<String> uris, LocalDateTime start, LocalDateTime end);

    Collection<EndpointHit> getEndpointHitsByTimestampBetween(LocalDateTime start, LocalDateTime end);

}