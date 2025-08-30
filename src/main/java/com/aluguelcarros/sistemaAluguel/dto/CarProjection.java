package com.aluguelcarros.sistemaAluguel.dto;

import java.math.BigDecimal;

public interface CarProjection {
    Long getId();
    String getModelo();
    String getMarca();     // Adicionado
    String getPlaca();
    Integer getAno();      // Adicionado
    Boolean getDisponivel(); // Mudado para Boolean (melhor para projections)
    BigDecimal getPrecoDiaria();
}