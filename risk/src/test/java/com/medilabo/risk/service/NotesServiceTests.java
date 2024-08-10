package com.medilabo.risk.service;

import com.medilabo.risk.dto.NoteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotesServiceTests {

    @InjectMocks
    private NotesService notesService;
    @Mock
    private WebClient webClient;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Test
    void testFetchNotesByPatId_Success() {
        NoteDto note = new NoteDto();
        note.setPatId(1);
        note.setNote("Sample note");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(NoteDto.class)).thenReturn(Flux.just(note));

        List<NoteDto> notes = notesService.fetchNotesByPatId(1);
        assertEquals(1, notes.size());
        assertEquals(1, notes.get(0).getPatId());
        assertEquals("Sample note", notes.get(0).getNote());
    }
}