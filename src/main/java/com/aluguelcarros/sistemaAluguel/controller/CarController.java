package com.aluguelcarros.sistemaAluguel.controller;

import com.aluguelcarros.sistemaAluguel.dto.CarProjection;
import com.aluguelcarros.sistemaAluguel.model.Car;
import com.aluguelcarros.sistemaAluguel.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    // GET /api/cars
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.findAll());
    }

    // GET /api/cars/available
    @GetMapping("/available")
    public ResponseEntity<List<CarProjection>> getAvailableCars() {
        return ResponseEntity.ok(carService.findAvailableProjected());
    }

    // POST /api/cars
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.save(car));
    }

    // PUT /api/cars/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.update(id, car));
    }

    // DELETE /api/cars/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
