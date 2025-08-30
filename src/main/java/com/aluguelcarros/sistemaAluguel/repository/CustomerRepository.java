package com.aluguelcarros.sistemaAluguel.repository;

import com.aluguelcarros.sistemaAluguel.dto.CustomerProjection;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Buscar cliente por email (único)
    Optional<Customer> findByEmail(String email);

    // Buscar clientes pelo nome (contendo parte do nome, case insensitive)
    List<Customer> findByFullNameContainingIgnoreCase(String namePart);

    // Buscar cliente pela CNH (driverLicenseNumber, único)
    Optional<Customer> findByDriverLicenseNumber(String driverLicenseNumber);

    // Buscar clientes pelo telefone exato
    List<Customer> findByPhoneNumber(String phoneNumber);

    // Buscar clientes por endereço (exemplo, pode ser útil para buscas por cidade/bairro)
    List<Customer> findByAddressContainingIgnoreCase(String addressPart);

    // Buscar clientes por faixa de idade (usando birthDate)
    List<Customer> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    // Projection: retornar apenas campos básicos
    List<CustomerProjection> findAllProjectedBy();
}
