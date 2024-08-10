package com.medilabo.risk.dto;

import com.medilabo.risk.constant.RiskLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiskDto {
    private Integer id;
    private RiskLevel level;
}
