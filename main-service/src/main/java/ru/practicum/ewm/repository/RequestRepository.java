package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Request;
import ru.practicum.ewm.model.User;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Request findByRequesterIdAndEventId(Integer userId, Integer eventId);

    Collection<Request> findAllByRequesterOrderByIdAsc(User user);

    Collection<Request> findAllByEventOrderByIdAsc(Event event);

    Collection<Request> findAllByIdInOrderById(Collection<Integer> requestIds);


}