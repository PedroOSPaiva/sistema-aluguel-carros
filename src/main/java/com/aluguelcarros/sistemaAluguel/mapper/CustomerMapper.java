package com.aluguelcarros.sistemaAluguel.mapper;

import com.aluguelcarros.sistemaAluguel.dto.CustomerRequestDTO;
import com.aluguelcarros.sistemaAluguel.dto.CustomerResponseDTO;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {
        if (dto == null) return null ;

        return new Customer(
                dto.getFullName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getDriverLicenseNumber(),
                dto.getBirthDate(),
                dto.getAddress()
        );
    }

    public CustomerResponseDTO toDTO(Customer entity) {
        if (entity == null) return null;

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setDriverLicenseNumber(entity.getDriverLicenseNumber());
        dto.setBirthDate(entity.getBirthDate());
        dto.setAddress(entity.getAddress());
        return dto;
    }
    // Novo método para atualizar uma entidade já existente com dados do DTO
    public void updateEntityFromDTO(CustomerRequestDTO dto, Customer entity) {
        if (dto == null || entity == null) return;

        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setDriverLicenseNumber(dto.getDriverLicenseNumber());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAddress(dto.getAddress());
    }
}
