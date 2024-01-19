package com.mashreq.assessment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${application.configs.booking.intervalMinutes:15}")
    private int bookingIntervalMinutes;

    public int getBookingIntervalMinutes() {
        return bookingIntervalMinutes;
    }
}
