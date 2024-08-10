package com.medilabo.risk.service;

import com.medilabo.risk.dto.NoteDto;
import com.medilabo.risk.exception.ServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
public class NotesService {

    @Autowired
    private WebClient webClient;

    public List<NoteDto> fetchNotesByPatId(Integer patId) {
        return webClient
                .get()
                .uri("patients/" + patId + "/notes")
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        clientResponse -> Mono.error(new ServerErrorException("Server notes error."))
                )
                .bodyToFlux(NoteDto.class)
                .doOnError(e -> log.error("Error to retrieve notes patient with patID {}", patId, e))
                .collectList()
                .block();
    }
}
