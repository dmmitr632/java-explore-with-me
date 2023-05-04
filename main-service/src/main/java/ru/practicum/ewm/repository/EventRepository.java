package ru.practicum.ewm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByInitiatorId(Integer id, Pageable pageable);

    List<Event> findAllByCategoryId(Integer id);

    List<Event> findAllByIdAndInitiator(Integer id, User user);

    List<Event> findAllByIdIn(Collection<Integer> eventsId);

    Page<Event> findAllByInitiatorOrderByIdAsc(User user, Pageable pageable);

//    @Query("SELECT e FROM Event AS e " +
//            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
//            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
//            "AND ((:states) IS NULL OR e.state IN :states) " +
//            "AND ((:start) IS null OR e.eventDate >= :start) " +
//            "AND ((:end) IS null OR e.eventDate <= :end) ")
//    Page<Event> getSelectedEvents(Collection<Integer> users, Collection<EventState> states,
//                                  Collection<Integer> categories,
//                                  LocalDateTime start, LocalDateTime end, Pageable pageable);
//

    Page<Event> findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
            Collection<Integer> users, Collection<EventState> states,
            Collection<Integer> categories,
            LocalDateTime start, LocalDateTime end, Pageable pageable);


    @Query("SELECT e FROM Event AS e " +
            "WHERE lower(e.annotation) like %:text% " +
            "OR lower(e.description) LIKE %:text% " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS null OR e.paid = :paid) " +
            "AND ((e.state) IN :eventState) " +
            "AND ((:start) IS null OR e.eventDate >= :start) " +
            "AND ((:end) IS null OR e.eventDate <= :end) ")
    List<Event> getEvents(String text, Collection<Integer> categories, Boolean paid,
                          EventState eventState, LocalDateTime start,
                          LocalDateTime end, Pageable pageable);
}