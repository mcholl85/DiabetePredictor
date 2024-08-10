package com.medilabo.risk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "risk")
@Getter
@Setter
public class RiskProperties {
    private List<String> terms;
}
