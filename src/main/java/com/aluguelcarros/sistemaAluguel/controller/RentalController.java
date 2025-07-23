package com.aluguelcarros.sistemaAluguel.controller;

import com.aluguelcarros.sistemaAluguel.model.Rental;
import com.aluguelcarros.sistemaAluguel.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // POST /api/rentals?customerId=1&carId=2&startDate=2025-07-22&endDate=2025-07-25
    @PostMapping
    public ResponseEntity<Rental> createRental(
            @RequestParam Long customerId,
            @RequestParam Long carId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Rental rental = rentalService.createRental(customerId, carId, startDate, endDate);
        return ResponseEntity.ok(rental);
    }

    // PUT /api/rentals/{id}/finish
    @PutMapping("/{id}/finish")
    public ResponseEntity<Rental> finishRental(@PathVariable Long id) {
        Rental rental = rentalService.finishRental(id);
        return ResponseEntity.ok(rental);
    }

    // GET /api/rentals
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        return ResponseEntity.ok(rentalService.findAllRentals());
    }

    // GET /api/rentals/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.findRentalById(id));
    }
}
