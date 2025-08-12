package com.base.Alerts_Service.setup;

import com.base.Alerts_Service.setup.Alert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {

    @Aggregation("{$group: {_id: '$type'}}")
    List<String> findDistinctAlertTypes();

    @Aggregation("{$group: {_id: '$status'}}")
    List<String> findDistinctAlertStatuses();

    @Query(value = "{}", sort = "{'createdAt': -1}")
    List<Alert> findAllOrderByCreatedAtDesc();

    List<Alert> findByTypeOrderByCreatedAtDesc(String type);

    List<Alert> findByStatusOrderByCreatedAtDesc(String status);

    List<Alert> findByCreatedByOrderByCreatedAtDesc(String createdBy);

    List<Alert> findByLocationOrderByCreatedAtDesc(String location);

    List<Alert> findByTypeAndCreatedAtBetweenOrderByCreatedAtDesc(
            String type, LocalDateTime startDate, LocalDateTime endDate);

    List<Alert> findByStatusAndCreatedAtBetweenOrderByCreatedAtDesc(
            String status, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{}", sort = "{'createdAt': -1}")
    List<Alert> findRecentAlerts(Pageable pageable);

    @Query(value = "{'status': 'active'}", sort = "{'createdAt': -1}")
    List<Alert> findActiveAlerts();

    @Query(value = "{'sentAt': {$exists: false}}", sort = "{'createdAt': -1}")
    List<Alert> findUnsentAlerts();

    @Query(value = "{'sentAt': {$exists: true}}", sort = "{'sentAt': -1}")
    List<Alert> findSentAlerts();
}