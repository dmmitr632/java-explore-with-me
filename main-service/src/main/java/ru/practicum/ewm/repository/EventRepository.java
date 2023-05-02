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

    Collection<Event> findAllByCategoryId(Integer id);

    Collection<Event> findAllByIdAndInitiator(Integer id, User user);

    Collection<Event> findAllByIdIn(Collection<Integer> eventsId);

    Collection<Event> findAllByInitiatorOrderByIdAsc(User user, Pageable pageable);


    //    @Query("select e from Event as e " +
//            "WHERE :usersIds IS null OR e.initiator.id in :usersIds " +
//            "AND :categories IS null OR e.category.id in :categories " +
//            "AND :start IS null OR e.eventDate >= :start " +
//            "AND :end IS null OR e.eventDate <= :end " +
//            "AND :states IS null OR e.eventState in :states ")
    @Query("SELECT e FROM Event AS e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories)" +
            "AND ((:states) IS NULL OR e.eventState IN :states) ")
    Page<Event> getSelectedEvents(Collection<Integer> usersIds, Collection<EventState> states,
                                  Collection<Integer> categories,
                                  LocalDateTime start, LocalDateTime end, Pageable pageable);

    //    @Query("select e from Event as e " +
//            "WHERE :text IS null OR lower(e.annotation) LIKE %:text% " +
//            "OR lower(e.description) LIKE %:text% " +
//            "AND :paid IS null OR e.paid = :paid " +
//            "AND :categories IS null OR e.category.id in :categories " +
//            "AND :start IS null OR e.eventDate >= :start " +
//            "AND :end IS null OR e.eventDate <= :end")
    @Query("SELECT e FROM Event AS e " +
            "WHERE lower(e.annotation) like %:text% " +
            "OR lower(e.description) LIKE %:text% " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS null OR e.paid = :paid) " +
            "AND e.eventState IN :eventState")
    Page<Event> getEvents(String text, Collection<Integer> categories, Boolean paid, LocalDateTime start,
                          LocalDateTime end, EventState eventState, Pageable pageable);
}