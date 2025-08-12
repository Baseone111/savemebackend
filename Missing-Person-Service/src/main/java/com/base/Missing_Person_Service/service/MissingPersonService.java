package com.base.Missing_Person_Service.service;


import com.base.Missing_Person_Service.dto.MissingPersonDto;
import com.base.Missing_Person_Service.dto.Response;
import com.base.Missing_Person_Service.exception.MyException;
import com.base.Missing_Person_Service.model.MissingPerson;
import com.base.Missing_Person_Service.repository.MissingPersonRepository;
import com.base.Missing_Person_Service.service.interfac.IMissingPersonService;
import com.base.Missing_Person_Service.utills.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissingPersonService implements IMissingPersonService {

    @Autowired
    private MissingPersonRepository missingPersonRepository;



    @Override
    public Response submitMissingPerson(String fullName, Integer age, MultipartFile attachment,
                                        String location, LocalDate lastSeenDate, String description, String relationship) {
        Response response = new Response();



            MissingPerson missingPerson = new MissingPerson();
            missingPerson.setFullName(fullName);
            missingPerson.setAge(age);
            missingPerson.setDescription(description);
            missingPerson.setRelationship(relationship);
            missingPerson.setLastSeenDate(lastSeenDate.atStartOfDay());

            missingPerson.setAttachmentUrl(String.valueOf(attachment));
            missingPerson.setLastSeenLocation(location);

            MissingPerson savedMissingPerson = missingPersonRepository.save(missingPerson);
            MissingPersonDto missingPersonDto = Utils.mapMissingPersonEntityToMissingPersonDto(savedMissingPerson);

            response.setStatusCode(200);
            response.setMessage("Missing Person reported successfully");
            response.setMissingPerson(missingPersonDto);


        return response;
    }

    @Override
    public List<String> getAllRelationships() {
        return missingPersonRepository.findAllByRelationship();
    }

    @Override
    public List<String> getAllLocations() {
        return missingPersonRepository.findAllByLastSeenLocation();
    }

    @Override
    public Response getAllMissingPersons() {
        Response response = new Response();

        try {
            List<MissingPerson> missingPersonList = missingPersonRepository.findAllOrderByLastSeenDateDesc();
            List<MissingPersonDto> missingPersonDtoList = Utils.mapMissingPersonListEntityToMissingPersonListDto(missingPersonList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setMissingPersonList(missingPersonDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all missing persons: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteMissingPerson(String missingPersonId) {
        Response response = new Response();

        try {
            missingPersonRepository.findById(missingPersonId)
                    .orElseThrow(() -> new MyException("Missing Person not found"));
            missingPersonRepository.deleteById(missingPersonId);

            response.setStatusCode(200);
            response.setMessage("Missing person deleted successfully");

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting missing person: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateMissingPerson(String missingPersonId, String fullName, Integer age,
                                        MultipartFile attachment, String location, LocalDate lastSeenDate,
                                        String description, String relationship) {
        Response response = new Response();



            MissingPerson missingPerson = missingPersonRepository.findById(missingPersonId)
                    .orElseThrow(() -> new MyException("Missing person not found"));

            // Update fields only if they're provided
            if (fullName != null && !fullName.isBlank()) missingPerson.setFullName(fullName);
            if (age != null) missingPerson.setAge(age);
            if (description != null && !description.isBlank()) missingPerson.setDescription(description);
            if (relationship != null && !relationship.isBlank()) missingPerson.setRelationship(relationship);
            if (location != null && !location.isBlank()) missingPerson.setLastSeenLocation(location);
            if (lastSeenDate != null) missingPerson.setLastSeenDate(lastSeenDate.atStartOfDay());

            if (attachment != null) missingPerson.setAttachmentUrl(String.valueOf(attachment));

            MissingPerson updatedMissingPerson = missingPersonRepository.save(missingPerson);
            MissingPersonDto missingPersonDto = Utils.mapMissingPersonEntityToMissingPersonDto(updatedMissingPerson);

            response.setStatusCode(200);
            response.setMessage("Missing person updated successfully");
            response.setMissingPerson(missingPersonDto);



        return response;
    }

    @Override
    public Response getMissingPersonById(String missingPersonId) {
        Response response = new Response();

        try {
            MissingPerson missingPerson = missingPersonRepository.findById(missingPersonId)
                    .orElseThrow(() -> new MyException("Missing Person not found"));
            MissingPersonDto missingPersonDto = Utils.mapMissingPersonEntityToMissingPersonDto(missingPerson);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setMissingPerson(missingPersonDto);

        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting missing person by id: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMissingPersonByAge(Integer age) {
        // Implementation needed if you want to filter by age
        Response response = new Response();
        response.setStatusCode(501);
        response.setMessage("Method not implemented");
        return response;
    }

    @Override
    public Response getRecentMissingPersons(int limit) {
        Response response = new Response();

        try {
            Pageable pageable = PageRequest.of(0, limit);
            List<MissingPerson> missingPersons = missingPersonRepository.findRecentMissingPerson(pageable);
            List<MissingPersonDto> missingPersonDtoList = Utils.mapMissingPersonListEntityToMissingPersonListDto(missingPersons);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setMissingPersonList(missingPersonDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting recent missing persons: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMissingPersonByRelationshipAndDateRange(String relationship, LocalDate startDate, LocalDate endDate) {
        Response response = new Response();

        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<MissingPerson> missingPersons = missingPersonRepository.findByRelationshipAndLastSeenDateBetweenOrderByLastSeenDateDesc(
                    relationship, startDateTime, endDateTime);
            List<MissingPersonDto> missingPersonDtoList = Utils.mapMissingPersonListEntityToMissingPersonListDto(missingPersons);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setMissingPersonList(missingPersonDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting missing persons by relationship and date range: " + e.getMessage());
        }

        return response;
    }
}