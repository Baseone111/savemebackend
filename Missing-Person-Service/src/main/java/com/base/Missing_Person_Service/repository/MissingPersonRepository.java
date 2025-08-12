package com.base.Missing_Person_Service.repository;

import com.base.Missing_Person_Service.model.MissingPerson;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MissingPersonRepository extends MongoRepository<MissingPerson, String> {

    @Query(value = "{}", fields = "{relationship: 1}", sort = "{}")
    List<String> findAllByRelationship();

    @Query(value = "{}", fields = "{lastSeenLocation: 1}", sort = "{}")
    List<String> findAllByLastSeenLocation();

    @Query(value = "{}", sort = "{'lastSeenDate': -1}")
    List<MissingPerson> findAllOrderByLastSeenDateDesc();

    List<MissingPerson> findByRelationshipOrderByLastSeenDateDesc(String relationship);

    List<MissingPerson> findByRelationshipAndLastSeenDateBetweenOrderByLastSeenDateDesc(
            String relationship, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{}", sort = "{'lastSeenDate': -1}")
    List<MissingPerson> findRecentMissingPerson(Pageable pageable);

    List<MissingPerson> findByAgeOrderByLastSeenDateDesc(Integer age);
}