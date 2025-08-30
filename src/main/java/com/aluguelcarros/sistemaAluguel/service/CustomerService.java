package com.aluguelcarros.sistemaAluguel.service;

import com.aluguelcarros.sistemaAluguel.dto.CustomerProjection;
import com.aluguelcarros.sistemaAluguel.dto.CustomerRequestDTO;
import com.aluguelcarros.sistemaAluguel.exception.ResourceNotFoundException;
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

    // CORRIGIDO: Retorna entidades para o controller usar o mapper
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    // CORRIGIDO: Retorna Optional<Customer> para o controller tratar
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    // CORRIGIDO: Recebe entidade e retorna entidade
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    // CORRIGIDO: Atualiza usando DTO mas retorna entidade
    public Customer update(Long id, @Valid CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id " + id));

        customerMapper.updateEntityFromDTO(dto, customer);
        return customerRepository.save(customer);
    }

    // Deleta cliente
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id " + id);
        }
        customerRepository.deleteById(id);
    }

    // CORRIGIDO: Retorna Optional<Customer>
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // CORRIGIDO: Retorna List<Customer>
    public List<Customer> findByName(String namePart) {
        return customerRepository.findByFullNameContainingIgnoreCase(namePart);
    }

    // Retorna projections (este método está correto)
    public List<CustomerProjection> findAllProjected() {
        return customerRepository.findAllProjectedBy();
    }
}