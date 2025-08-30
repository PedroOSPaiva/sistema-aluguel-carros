package com.aluguelcarros.sistema_aluguel;


import com.aluguelcarros.sistemaAluguel.model.Car;
import com.aluguelcarros.sistemaAluguel.model.Customer;
import com.aluguelcarros.sistemaAluguel.service.CarService;
import com.aluguelcarros.sistemaAluguel.service.CustomerService;
import com.aluguelcarros.sistemaAluguel.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CarRentalSystemTest implements CommandLineRunner {

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentalService rentalService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚗 TESTANDO SISTEMA DE ALUGUEL DE CARROS");

        try {
            // 1. CRIAR CARROS DE TESTE
            System.out.println("\n📝 Criando carros...");
            Car car1 = new Car("Civic", "Honda", "ABC-1234", 2023, true, new BigDecimal("150.00"));
            Car car2 = new Car("Corolla", "Toyota", "XYZ-5678", 2022, true, new BigDecimal("140.00"));

            car1 = carService.save(car1);
            car2 = carService.save(car2);
            System.out.println(" Carros criados: " + car1.getId() + ", " + car2.getId());

            // 2. CRIAR CLIENTES DE TESTE
            System.out.println("\n Criando clientes...");
            Customer customer1 = new Customer(
                    "João Silva",
                    "joao@email.com",
                    "(83) 99999-9999",
                    "12345678901",
                    LocalDate.of(1990, 5, 15),
                    "Rua das Flores, 123"
            );

            customer1 = customerService.create(customer1);
            System.out.println(" Cliente criado: " + customer1.getId());

            // 3. TESTAR PROJECTIONS
            System.out.println("\n Testando projections...");

            // Carros disponíveis
            var availableCars = carService.findAvailableProjected();
            System.out.println(" Carros disponíveis (projection): " + availableCars.size());
            availableCars.forEach(car ->
                    System.out.println("  - " + car.getModelo() + " (" + car.getPlaca() + ") - R$ " + car.getPrecoDiaria())
            );

            // Clientes projection
            var customersProjection = customerService.findAllProjected();
            System.out.println("👤 Clientes (projection): " + customersProjection.size());
            customersProjection.forEach(customer ->
                    System.out.println("  - " + customer.getFullName() + " (" + customer.getEmail() + ")")
            );

            // 4. CRIAR ALUGUEL DE TESTE
            System.out.println("\n Criando aluguel...");
            var rental = rentalService.createRental(
                    customer1.getId(),
                    car1.getId(),
                    LocalDate.now().plusDays(1),
                    LocalDate.now().plusDays(5)
            );
            System.out.println(" Aluguel criado: " + rental.getId() + " - Total: R$ " + rental.getTotalPrice());

            // 5. TESTAR RENTAL PROJECTIONS
            System.out.println("\n Testando rental projections...");
            var rentalsProjected = rentalService.findAllRentalsProjected();
            System.out.println(" Aluguéis (projection): " + rentalsProjected.size());
            rentalsProjected.forEach(r ->
                    System.out.println("  - " + r.getCustomerFullName() + " alugou " + r.getCarModelo() +
                            " (" + r.getStartDate() + " a " + r.getEndDate() + ") - R$ " + r.getTotalPrice())
            );

            System.out.println("\n TODOS OS TESTES CONCLUÍDOS COM SUCESSO!");

        } catch (Exception e) {
            System.err.println(" ERRO NO TESTE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}