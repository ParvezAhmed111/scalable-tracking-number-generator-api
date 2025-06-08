package com.project.tracking.tracking_number_generator.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;

public class TrackingRequestValidator {
    private TrackingRequestValidator() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingRequestValidator.class);

    private static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,3})?$");
    private static final Pattern CUSTOMER_SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(-[a-z0-9]+)*$");

    public static void validateOriginCountryId(String originCountryId) {
        LOGGER.debug("Validating origin_country_id: {}", originCountryId);
        if (!COUNTRY_CODE_PATTERN.matcher(originCountryId).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid origin_country_id format");
        }
    }

    public static void validateDestinationCountryId(String destinationCountryId) {
        LOGGER.debug("Validating destination_country_id: {}", destinationCountryId);
        if (!COUNTRY_CODE_PATTERN.matcher(destinationCountryId).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid destination_country_id format");
        }
    }

    public static void validateWeight(String weightStr) {
        LOGGER.debug("Validating weight: {}", weightStr);
        if (weightStr == null || !WEIGHT_PATTERN.matcher(weightStr).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid weight format");
        }
        double weight;
        try {
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid weight format");
        }
        if (weight <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Weight must be positive");
        }
    }

    public static void validateCreatedAt(String createdAt) {
        LOGGER.debug("Validating created_at: {}", createdAt);
        try {
            OffsetDateTime.parse(createdAt);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid created_at timestamp");
        }
    }

    public static void validateCustomerId(String customerId) {
        LOGGER.debug("Validating customer_id: {}", customerId);
        try {
            UUID.fromString(customerId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer_id format");
        }
    }

    public static void validateCustomerName(String customerName) {
        LOGGER.debug("Validating customer_name: {}", customerName);
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer_name cannot be empty");
        }
    }

    public static void validateCustomerSlug(String customerSlug) {
        LOGGER.debug("Validating customer_slug: {}", customerSlug);
        if (!CUSTOMER_SLUG_PATTERN.matcher(customerSlug).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer_slug format");
        }
    }
}
