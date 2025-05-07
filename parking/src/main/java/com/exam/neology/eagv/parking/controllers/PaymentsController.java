package com.exam.neology.eagv.parking.controllers;

import com.exam.neology.eagv.parking.services.PaymentsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping("/by/{catVehicle}/{nameFile}")
    public ResponseEntity<InputStreamResource> generateReportPayments(
            @PathVariable Integer catVehicle,
            @PathVariable String nameFile
    ) throws IOException {

        String csvData = paymentsService.generateReportBy(catVehicle);

        InputStreamResource resource = new InputStreamResource(
                new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8)));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nameFile + ".csv");
        headers.setContentType(MediaType.parseMediaType("text/csv"));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(csvData.getBytes(StandardCharsets.UTF_8).length)
                .body(resource);
    }

}
