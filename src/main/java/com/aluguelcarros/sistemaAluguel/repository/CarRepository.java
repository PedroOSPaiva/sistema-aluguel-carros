package com.aluguelcarros.sistemaAluguel.repository;

import com.aluguelcarros.sistemaAluguel.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByDisponivelTrue();
}
