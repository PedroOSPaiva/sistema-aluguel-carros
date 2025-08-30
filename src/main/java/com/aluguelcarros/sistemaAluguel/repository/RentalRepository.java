package com.aluguelcarros.sistemaAluguel.repository;

import com.aluguelcarros.sistemaAluguel.dto.RentalProjection;
import com.aluguelcarros.sistemaAluguel.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByCustomerId(Long customerId);

    // QUERY CUSTOMIZADA PARA PROJECTION COM JOINS
    @Query("""
        SELECT r.id as id,
               r.startDate as startDate,
               r.endDate as endDate,
               r.totalPrice as totalPrice,
               r.status as status,
               c.modelo as carModelo,
               c.placa as carPlaca,
               cu.fullName as customerFullName,
               cu.email as customerEmail
        FROM Rental r
        JOIN r.car c
        JOIN r.customer cu
        """)
    List<RentalProjection> findAllProjected();

    // PROJECTION PARA ALUGUÉIS ATIVOS
    @Query("""
        SELECT r.id as id,
               r.startDate as startDate,
               r.endDate as endDate,
               r.totalPrice as totalPrice,
               r.status as status,
               c.modelo as carModelo,
               c.placa as carPlaca,
               cu.fullName as customerFullName,
               cu.email as customerEmail
        FROM Rental r
        JOIN r.car c
        JOIN r.customer cu
        WHERE r.status = 'ACTIVE'
        """)
    List<RentalProjection> findActiveRentalsProjected();
}