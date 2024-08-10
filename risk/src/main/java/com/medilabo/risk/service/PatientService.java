package com.medilabo.risk.service;

import com.medilabo.risk.dto.PatientDto;
import com.medilabo.risk.exception.PatientNotFoundException;
import com.medilabo.risk.exception.ServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class PatientService {
    @Autowired
    private WebClient webClient;

    public PatientDto fetchPatientById(Integer id) {
        return webClient
                .get()
                .uri("patients/" + id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> Mono.error(new PatientNotFoundException("Patient with ID " + id + " not found."))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new ServerErrorException("Server patient error."))
                )
                .bodyToMono(PatientDto.class)
                .doOnError(e -> log.error("Error to retrieve patient with ID {}", id, e))
                .block();
    }
}
