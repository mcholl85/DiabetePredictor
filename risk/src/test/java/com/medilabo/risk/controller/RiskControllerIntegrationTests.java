package com.medilabo.risk.controller;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RiskControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws IOException {
        if (mockWebServer == null) {
            mockWebServer = new MockWebServer();
            mockWebServer.start(8080);
        }

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                String path = request.getPath();

                assert path != null;
                return switch (path) {
                    case "/patients/1" -> new MockResponse()
                            .setBody("{\"id\":1,\"birthDate\":\"1966-12-31\",\"gender\":\"F\"}")
                            .addHeader("Content-Type", "application/json");
                    case "/patients/2" -> new MockResponse()
                            .setBody("{\"id\":2,\"birthDate\":\"1970-01-01\",\"gender\":\"M\"}")
                            .addHeader("Content-Type", "application/json");
                    case "/patients/3" -> new MockResponse()
                            .setResponseCode(500);
                    case "/patients/1/notes" -> new MockResponse()
                            .setBody("[{\"patId\":1,\"note\":\"Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé\"}]")
                            .addHeader("Content-Type", "application/json");
                    case "/patients/2/notes", "/patients/3/notes", "/patients/4/notes" -> new MockResponse()
                            .setBody("[]")
                            .addHeader("Content-Type", "application/json");
                    default -> new MockResponse().setResponseCode(404);
                };
            }
        };
        mockWebServer.setDispatcher(dispatcher);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @WithMockUser
    void testGetRiskById_Success() throws Exception {
        mockMvc.perform(get("/risk/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.level", is("NONE")));
    }

    @Test
    @WithMockUser
    void testGetRiskById_PatientNotFound() throws Exception {
        mockMvc.perform(get("/risk/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Patient with ID 4 not found.")));
    }

    @Test
    @WithMockUser
    void testGetRiskById_ServerError() throws Exception {
        mockMvc.perform(get("/risk/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Server patient error.")));
    }

    @Test
    @WithMockUser
    void testGetRiskById_EmptyNotes() throws Exception {
        mockMvc.perform(get("/risk/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", is("NONE")));
    }
}