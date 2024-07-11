package com.medilabo.patient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = PatientApplication.class)
public class PatientApplicationTests {

    @Test
    void contextLoads() {}
}
