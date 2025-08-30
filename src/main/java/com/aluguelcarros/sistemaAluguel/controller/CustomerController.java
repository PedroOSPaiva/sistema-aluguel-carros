package com.aluguelcarros.sistemaAluguel.controller;

import com.aluguelcarros.sistemaAluguel.dto.CustomerProjection;
import com.aluguelcarros.sistemaAluguel.dto.CustomerRequestDTO;
import com.aluguelcarros.sistemaAluguel.dto.CustomerResponseDTO;
import com.aluguelcarros.sistemaAluguel.mapper.CustomerMapper;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import com.aluguelcarros.sistemaAluguel.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<CustomerResponseDTO> dtos = customerService.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(customerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        Customer saved = customerService.create(customer);
        return ResponseEntity.ok(customerMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto) {
        try {
            Customer updated = customerService.update(id, dto);
            return ResponseEntity.ok(customerMapper.toDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchByName(@RequestParam String name) {
        List<CustomerResponseDTO> dtos = customerService.findByName(name)
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerResponseDTO> getByEmail(@RequestParam String email) {
        return customerService.findByEmail(email)
                .map(customerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/projection")
    public ResponseEntity<List<CustomerProjection>> getAllCustomersProjection() {
        return ResponseEntity.ok(customerService.findAllProjected());
    }
}