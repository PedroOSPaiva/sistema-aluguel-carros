package com.aluguelcarros.sistemaAluguel.service;

import com.aluguelcarros.sistemaAluguel.dto.CarProjection;
import com.aluguelcarros.sistemaAluguel.model.Car;
import com.aluguelcarros.sistemaAluguel.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<Car> findAvailable() {
        return carRepository.findByDisponivelTrue();
    }

    public List<CarProjection> findAvailableProjected() {
        return carRepository.findAllByDisponivelTrue();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car update(Long id, Car updatedCar) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        car.setModelo(updatedCar.getModelo());
        car.setMarca(updatedCar.getMarca());
        car.setPlaca(updatedCar.getPlaca());
        car.setAno(updatedCar.getAno());
        car.setDisponivel(updatedCar.isDisponivel());
        car.setPrecoDiaria(updatedCar.getPrecoDiaria());
        return carRepository.save(car);
    }

    public void delete(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found");
        }
        carRepository.deleteById(id);
    }
}
