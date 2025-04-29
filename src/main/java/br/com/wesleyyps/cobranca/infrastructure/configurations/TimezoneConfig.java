package br.com.wesleyyps.cobranca.infrastructure.configurations;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimezoneConfig {
    @Value("${app.jackson.time-zone}")
    private String timezone;

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(this.timezone));
    }
}
