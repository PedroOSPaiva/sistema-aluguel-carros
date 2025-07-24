package com.aluguelcarros.sistemaAluguel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Datas do aluguel
    private LocalDate startDate;
    private LocalDate endDate;

    // Valor total do aluguel
    private BigDecimal totalPrice;

    // Relacionamento com o carro
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    // Relacionamento com o cliente
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Status do aluguel (ex: ATIVO, FINALIZADO, CANCELADO)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    // Construtor padrão (JPA)
    public Rental() {}

    // Construtor completo (exceto ID)
    public Rental(LocalDate startDate, LocalDate endDate, BigDecimal totalPrice, Car car, Customer customer, RentalStatus status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.car = car;
        this.customer = customer;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RentalStatus getStatus() {
        return status;
    }
    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                ", car=" + car +
                ", customer=" + customer +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
