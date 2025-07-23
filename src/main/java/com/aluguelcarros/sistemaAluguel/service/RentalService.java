package com.aluguelcarros.sistemaAluguel.service;

import com.aluguelcarros.sistemaAluguel.exception.ResourceNotFoundException;
import com.aluguelcarros.sistemaAluguel.model.Car;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import com.aluguelcarros.sistemaAluguel.model.Rental;
import com.aluguelcarros.sistemaAluguel.model.RentalStatus;
import com.aluguelcarros.sistemaAluguel.repository.CarRepository;
import com.aluguelcarros.sistemaAluguel.repository.CustomerRepository;
import com.aluguelcarros.sistemaAluguel.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Rental createRental(Long customerId, Long carId, LocalDate startDate, LocalDate endDate) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado"));

        if (!car.isDisponivel()) {
            throw new IllegalStateException("Carro não está disponível para aluguel");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        long dias = ChronoUnit.DAYS.between(startDate, endDate);
        if (dias <= 0) {
            throw new IllegalArgumentException("A data de término deve ser posterior à data de início.");
        }

        BigDecimal precoDiaria = car.getPrecoDiaria(); // Este campo precisa existir na entidade Car
        BigDecimal total = precoDiaria.multiply(BigDecimal.valueOf(dias));

        Rental rental = new Rental(startDate, endDate, total, car, customer, RentalStatus.ACTIVE);

        car.setDisponivel(false); // Marca o carro como alugado
        carRepository.save(car);
        return rentalRepository.save(rental);
    }

    public Rental finishRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado"));

        if (rental.getStatus() != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Aluguel já finalizado ou cancelado.");
        }

        rental.setStatus(RentalStatus.COMPLETED);

        Car car = rental.getCar();
        car.setDisponivel(true); // Libera o carro

        carRepository.save(car);
        return rentalRepository.save(rental);
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental findRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aluguel não encontrado"));
    }

}
