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
//    Page<Event> getSelectedEventsAdmin(Collection<Integer> users, Collection<EventState> states,
//                                  Collection<Integer> categories,
//                                  LocalDateTime start, LocalDateTime end, Pageable pageable);
//

    Page<Event> findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
            Collection<Integer> users, Collection<EventState> states,
            Collection<Integer> categories,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

//error
// select event0_.id as id1_2_, event0_.annotation as annotati2_2_,
// event0_.category_id as categor13_2_,
// event0_.created_on as created_3_2_,
// event0_.description as descript4_2_,
// event0_.event_date as event_da5_2_,
// event0_.initiator_id as initiat14_2_,
// event0_.location_id as locatio15_2_,
// event0_.paid as paid6_2_,
// event0_.participant_limit as particip7_2_,
// event0_.published_on as publishe8_2_,
// event0_.request_moderation as request_9_2_,
// event0_.state as state10_2_,
// event0_.title as title11_2_,
// event0_.views as views12_2_ from events event0_
// where ($1 is null or event0_.initiator_id in ($2))
// and ($3 is null or event0_.category_id in ($4))
// and ($5 is null or event0_.state in ($6))
// and ($7 is null or event0_.event_date>=$8)
// and ($9 is null or event0_.event_date<=$10) limit $11


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