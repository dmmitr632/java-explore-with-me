package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;

import java.util.Collection;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    ParticipationRequest findByRequesterIdAndEventId(Integer userId, Integer eventId);

    Collection<ParticipationRequest> findAllByRequesterId(Integer userId);

    Collection<ParticipationRequest> findAllByRequesterOrderByIdAsc(User user);

    Collection<ParticipationRequest> findAllByEventOrderByIdAsc(Event event);

    Collection<ParticipationRequest> findAllByIdInOrderById(Collection<Integer> requestIds);


}