package com.project.tracking.tracking_number_generator.controller;

import com.project.tracking.tracking_number_generator.dto.TrackingResponse;
import com.project.tracking.tracking_number_generator.service.TrackingNumberService;
import com.project.tracking.tracking_number_generator.utility.TrackingRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackingNumberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingNumberController.class);

    private final TrackingNumberService trackingNumberService;

    public TrackingNumberController(TrackingNumberService trackingNumberService) {
        this.trackingNumberService = trackingNumberService;
    }

    @GetMapping("/next-tracking-number")
    public ResponseEntity<TrackingResponse> getNextTrackingNumber(
            @RequestParam(name = "origin_country_id") String originCountryId,
            @RequestParam(name = "destination_country_id") String destinationCountryId,
            @RequestParam(name = "weight") String weightStr,
            @RequestParam(name = "created_at") String createdAt,
            @RequestParam(name = "customer_id") String customerId,
            @RequestParam(name = "customer_name") String customerName,
            @RequestParam(name = "customer_slug") String customerSlug) {
        LOGGER.info("Entering TrackingNumberController.getNextTrackingNumber() with parameters: originCountryId={}, destinationCountryId={}, weight={}, createdAt={}, customerId={}, customerName={}, customerSlug={}",
                originCountryId, destinationCountryId, weightStr, createdAt, customerId, customerName, customerSlug);

        validateQueryParams(originCountryId, destinationCountryId, weightStr, createdAt, customerId, customerName, customerSlug);

        TrackingResponse trackingResponse = trackingNumberService.generateTrackingNumber();
        return ResponseEntity.ok(trackingResponse);
    }

    private static void validateQueryParams(String originCountryId, String destinationCountryId, String weightStr, String createdAt, String customerId, String customerName, String customerSlug) {
        TrackingRequestValidator.validateOriginCountryId(originCountryId);
        TrackingRequestValidator.validateDestinationCountryId(destinationCountryId);
        TrackingRequestValidator.validateWeight(weightStr);
        TrackingRequestValidator.validateCreatedAt(createdAt);
        TrackingRequestValidator.validateCustomerId(customerId);
        TrackingRequestValidator.validateCustomerName(customerName);
        TrackingRequestValidator.validateCustomerSlug(customerSlug);
    }
}
