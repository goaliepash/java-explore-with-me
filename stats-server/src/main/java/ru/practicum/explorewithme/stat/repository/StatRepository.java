package ru.practicum.explorewithme.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.stat.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT eh FROM EndpointHit AS eh " +
            "WHERE eh.timestamp BETWEEN :startDateTime AND :endDateTime " +
            "AND eh.uri IN :uris")
    List<EndpointHit> findAll(LocalDateTime startDateTime, LocalDateTime endDateTime, List<String> uris);

    @Query("SELECT COUNT(eh.app) FROM EndpointHit AS eh WHERE eh.app = :appName GROUP BY eh.app")
    Long countByApp(String appName);
}