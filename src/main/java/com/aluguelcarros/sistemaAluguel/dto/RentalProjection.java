package com.aluguelcarros.sistemaAluguel.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RentalProjection {
    Long getId();
    LocalDate getStartDate();
    LocalDate getEndDate();
    BigDecimal getTotalPrice();
    String getStatus();

    // Informações do carro
    String getCarModelo();
    String getCarPlaca();

    // Informações do cliente
    String getCustomerFullName();
    String getCustomerEmail();
}
