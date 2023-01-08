package ru.practicum.explorewithme.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.request.model.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request AS r WHERE r.event.id = :eventId")
    List<Request> getEventRequests(long eventId);

    @Query("SELECT r FROM Request AS r WHERE r.event.id = :eventId AND r.status = :status")
    List<Request> getEventRequestsByStatus(long eventId, RequestStatus status);

    @Query("SELECT r FROM Request AS r WHERE r.requester.id = :requesterId")
    List<Request> findAllByRequesterId(long requesterId);
}