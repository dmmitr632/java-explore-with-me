package ru.practicum.ewm.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findAllByInitiatorId(Integer id, Pageable pageable);

    List<Event> findAllByCategoryId(Integer id);

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
            "AND (coalesce(:start , null) IS NULL OR e.eventDate >= :start) " + // без coalesce ругается на
            // неправильный тип данных
            "AND (coalesce(:end , null) IS NULL OR e.eventDate <= :end) ")
    List<Event> getEvents(String text, Collection<Integer> categories, Boolean paid,
                          EventState eventState, LocalDateTime start,
                          LocalDateTime end, Pageable pageable);
}