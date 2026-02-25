package com.kfh.healthcare.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${app.cache.doctors.expiry-minutes}") // :5 provides a default value
    private int doctorExpiry;

    @Value("${app.cache.doctors.max-size}")
    private int doctorMaxSize;

    @Value("${app.cache.patient.expiry-minutes}") // :5 provides a default value
    private int patientExpiry;

    @Value("${app.cache.patient.max-size}")
    private int patientMaxSize;

    @Value("${app.jwt.expiration-ms}")
    private Integer tokenExpirationMins;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("doctors","patients");

        cacheManager.registerCustomCache("doctors", Caffeine.newBuilder()
                .expireAfterWrite(doctorExpiry, TimeUnit.MINUTES)
                .maximumSize(doctorMaxSize)
                .recordStats()
                .build());

        cacheManager.registerCustomCache("patients", Caffeine.newBuilder()
                .expireAfterWrite(patientExpiry, TimeUnit.HOURS)
                .maximumSize(patientMaxSize)
                .build());

        cacheManager.registerCustomCache("blacklistedTokens", Caffeine.newBuilder()
                .expireAfterWrite(tokenExpirationMins, TimeUnit.MINUTES)
                .maximumSize(10000)
                .build());

        return cacheManager;
    }
}
