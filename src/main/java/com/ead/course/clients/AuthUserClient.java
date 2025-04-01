package com.ead.course.clients;

import com.ead.course.dtos.CourseUserRecordDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserRecordDto;
import com.ead.course.exceptions.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class AuthUserClient {

    Logger logger = LogManager.getLogger(AuthUserClient.class);

    @Value("${ead.api.url.authuser}")
    String baseUrlAuthuser;

    final RestClient restClient;

    public AuthUserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<UserRecordDto> getAllUsersByCourse(UUID courseId, Pageable pageable){
        String url =   baseUrlAuthuser + "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
        logger.debug("Request URL: {} ", url);

        try{
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDto<UserRecordDto>>() {});

        } catch (RestClientException e){
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }
    }

    public ResponseEntity<UserRecordDto> getOneUserById(UUID userId){
        String url = baseUrlAuthuser + "/users/" + userId;
        logger.debug("Request URL: {} ", url);

        return restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    logger.error("Error: User not found: {} ", userId);
                    throw new NotFoundException("Error: User not found.");
                })
                .toEntity(UserRecordDto.class);
    }


    // tambem é uma chamada via Sincrona para microsservice Authuser
    public void postSubscriptionUserInCourse(UUID courseId, UUID userId){
        String url = baseUrlAuthuser + "/users/" + userId + "courses/subscriptions";
        logger.debug("Request URL: {} ", url);

        try {
            var courseUserRecordDto = new CourseUserRecordDto(courseId, userId);
            restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(courseUserRecordDto)
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException e) {
            logger.error("Error Request POST RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request POST RestClient", e);
        }
    }

    public void deleteCourseUserInAuthuser(UUID courseId){
        String url = baseUrlAuthuser + "/users/course" + courseId;
        logger.debug("Request URL: {} ", url);

        try {
            restClient.delete()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity();
        }catch (RestClientException e){
            logger.error("Error Request DELETE RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request DELETE RestClient", e);
        }
    }


}
