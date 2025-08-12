package com.saveme.ReportIncident_Service.repository;


import com.saveme.ReportIncident_Service.model.IncidentReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentRepository extends MongoRepository<IncidentReport, String> {

    @Aggregation("{$group: {_id: '$incidentType'}}")
    List<String> findDistinctIncidentTypes();

    @Aggregation("{$group: {_id: '$urgency'}}")
    List<String> findDistinctUrgencyLevels();

    @Query(value = "{}", sort = "{'reportedAt': -1}")
    List<IncidentReport> findAllOrderByReportedAtDesc();

    List<IncidentReport> findByUrgencyOrderByReportedAtDesc(String urgency);

    List<IncidentReport> findByIncidentTypeAndReportedAtBetweenOrderByReportedAtDesc(
            String incidentType, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{}", sort = "{'reportedAt': -1}")
    List<IncidentReport> findRecentIncidents(Pageable pageable);
}