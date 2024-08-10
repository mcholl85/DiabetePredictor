package com.medilabo.risk.dto;

import lombok.Data;

@Data
public class NoteDto {
        private String id;
        private Integer patId;
        private String patient;
        private String note;
}
