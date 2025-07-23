package com.aluguelcarros.sistemaAluguel.repository;

import com.aluguelcarros.sistemaAluguel.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByCustomerId(Long customerId);
}
