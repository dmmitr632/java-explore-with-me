package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.dto.EventConfirmedRequestDto;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    ParticipationRequest findByRequesterIdAndEventId(Integer userId, Integer eventId);

    List<ParticipationRequest> findAllByRequesterId(Integer userId);


    Integer countParticipationByEventIdAndStatus(Integer id, RequestStatus status);

    List<ParticipationRequest> findAllByEventInitiatorIdAndEventId(Integer userId, Integer eventId);

    List<ParticipationRequest> findByEventAndStatus(Event event, RequestStatus requestStatus);

    List<ParticipationRequest> findByIdInOrderById(List<Integer> requestIds);

    @Query("SELECT new ru.practicum.ewm.dto.EventConfirmedRequestDto(pr.event.id, count(pr.id)) " +
            "FROM ParticipationRequest AS pr " +
            "WHERE pr.event.id IN ?1 " +
            "AND pr.status = 'CONFIRMED' " +
            "GROUP BY pr.event.id")
    List<EventConfirmedRequestDto> getConfirmedRequests(List<Integer> eventsIds);

}