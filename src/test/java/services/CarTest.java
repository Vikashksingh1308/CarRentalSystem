package services;

import org.example.models.Car;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CarTest {
    @Test
    public void testCarCreation(){
        Car car = new Car("CAR001", "Mazda 2", Car.CarType.SEDAN, 45.00);
        assertEquals("CAR001", car.getId());
        assertEquals("Mazda 2", car.getModel());
        assertEquals(Car.CarType.SEDAN, car.getCarType());
        assertEquals(45, car.getPricePerDay());
        assertTrue(car.isAvailable());
    }

    @Test
    public void testSetRentalPeriod() {
        Car car = new Car("CAR002", "Tesla S", Car.CarType.SEDAN, 58.00);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 10, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 15, 10, 0);
        car.setRentalPeriod(startDate, endDate);

        assertEquals(startDate, car.getRentalStartDate());
        assertEquals(endDate, car.getRentalEndDate());
    }
}
