package com.project.tracking.tracking_number_generator.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.project.tracking.tracking_number_generator.dto.TrackingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static com.project.tracking.tracking_number_generator.constants.ServiceConstants.ALPHABETS_AND_DIGITS;

@Service
public class TrackingNumberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingNumberService.class);

    private static final char[] ALPHABET = ALPHABETS_AND_DIGITS.toCharArray();
    private static final int LENGTH = 16;
    private final SecureRandom random = new SecureRandom();

    public TrackingResponse generateTrackingNumber() {
        LOGGER.info("Generating new tracking number");
        String trackingNumber = NanoIdUtils.randomNanoId(random, ALPHABET, LENGTH);
        return new TrackingResponse(trackingNumber, java.time.OffsetDateTime.now().toString());
    }
}