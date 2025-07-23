package com.aluguelcarros.sistemaAluguel.service;

import com.aluguelcarros.sistemaAluguel.dto.CustomerRequestDTO;
import com.aluguelcarros.sistemaAluguel.mapper.CustomerMapper;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import com.aluguelcarros.sistemaAluguel.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Long id, @Valid CustomerRequestDTO updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setFullName(updatedCustomer.getFullName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setDriverLicenseNumber(updatedCustomer.getDriverLicenseNumber());
            customer.setBirthDate(updatedCustomer.getBirthDate());
            customer.setAddress(updatedCustomer.getAddress());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Cliente não encontrado com id " + id));
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> findByName(String namePart) {
        return customerRepository.findByFullNameContainingIgnoreCase(namePart);
    }

}
