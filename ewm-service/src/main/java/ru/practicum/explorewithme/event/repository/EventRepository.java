package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event AS e WHERE e.state = :state " +
            "AND (:text IS NULL OR e.annotation LIKE %:text% OR e.description LIKE %:text%) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (CAST(:rangeStart AS date) IS NULL OR e.eventDate >= CAST(:rangeStart AS date)) " +
            "AND (CAST(:rangeEnd AS date) IS NULL OR e.eventDate <= CAST(:rangeEnd AS date)) " +
            "AND (:onlyAvailable = FALSE OR e.confirmedRequests < e.participantLimit)"
    )
    List<Event> findAll(
            EventState state,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            Pageable pageable
    );

    @Query("SELECT e FROM Event AS e WHERE e.initiator.id = :userId")
    List<Event> findAll(long userId, Pageable pageable);

    @Query("SELECT e FROM Event AS e WHERE (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR e.state IN :states)" +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (CAST(:start AS date) IS NULL OR e.eventDate >= CAST(:start AS date)) " +
            "AND (CAST(:end AS date) IS NULL OR e.eventDate <= CAST(:end AS date))")
    List<Event> findAll(
            List<Long> users,
            List<EventState> states,
            List<Long> categories,
            LocalDateTime start,
            LocalDateTime end,
            PageRequest pageRequest
    );
}