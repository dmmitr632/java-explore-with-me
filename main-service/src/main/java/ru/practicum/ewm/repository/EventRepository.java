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

public interface EventRepository extends JpaRepository<Event, Integer> {

    Collection<Event> findAllByInitiatorId(Integer id, Pageable pageable);

    Collection<Event> findAllByIdAndInitiator(Integer id, User user);

    Collection<Event> findAllByCategoryId(Integer id);

    Collection<Event> findAllByIdIn(Collection<Integer> eventsId);

    Collection<Event> findAllByInitiatorOrderByIdAsc(User user, Pageable pageable);

    @Query("select e from Event as e " +
            "WHERE :usersIds IS null OR e.initiator.id in :usersIds " +

            "AND :categories IS null OR e.category.id in :categories " +
            "AND :start IS null OR e.eventDate >= :start " +
            "AND :end IS null OR e.eventDate <= :end " +
            "AND :states IS null OR e.eventState in :states ")
    Page<Event> getSelectedEvents(Collection<Integer> usersIds, Collection<EventState> states,
                                  Collection<Integer> categories,
                                  LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("select e from Event as e " +
            "WHERE :text IS null OR lower(e.annotation) like %:text% " +
            "OR lower(e.description) like %:text% " +
            "AND :paid IS null OR e.paid = :paid " +
            "AND :categories IS null OR e.category.id in :categories " +
            "AND :start IS null OR e.eventDate >= :start " +
            "AND :end IS null OR e.eventDate <= :end")
    Collection<Event> getEvents(String text, Collection<Integer> categories, Boolean paid, LocalDateTime start,
                                LocalDateTime end, Pageable pageable);
}