package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;

import java.util.Collection;
import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    ParticipationRequest findByRequesterIdAndEventId(Integer userId, Integer eventId);

    List<ParticipationRequest> findAllByRequesterId(Integer userId);

    List<ParticipationRequest> findAllByRequesterOrderByIdAsc(User user);

    List<ParticipationRequest> findAllByEventOrderByIdAsc(Event event);

    List<ParticipationRequest> findAllByIdInOrderById(Collection<Integer> requestIds);

    Integer countParticipationByEventIdAndStatus(Integer id, RequestStatus requestStatus);
}