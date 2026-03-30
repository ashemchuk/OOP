package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.ashemchuk.config.BakerConfig;
import ru.ashemchuk.config.CourierConfig;
import ru.ashemchuk.config.PizzeriaConfig;

class PizzeriaTest {
    private PizzeriaConfig config;
    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() {
        config = mock(PizzeriaConfig.class);

        when(config.warehouseCapacity()).thenReturn(5);
        when(config.workingTime()).thenReturn(2000);

        BakerConfig baker1 = mock(BakerConfig.class);
        when(baker1.id()).thenReturn(1);
        when(baker1.workingTime()).thenReturn(200);

        BakerConfig baker2 = mock(BakerConfig.class);
        when(baker2.id()).thenReturn(2);
        when(baker2.workingTime()).thenReturn(300);

        when(config.bakers()).thenReturn(List.of(baker1, baker2));

        CourierConfig courier1 = mock(CourierConfig.class);
        when(courier1.id()).thenReturn(1);
        when(courier1.trunkCapacity()).thenReturn(3);
        when(courier1.deliveryTime()).thenReturn(400);

        CourierConfig courier2 = mock(CourierConfig.class);
        when(courier2.id()).thenReturn(2);
        when(courier2.trunkCapacity()).thenReturn(2);
        when(courier2.deliveryTime()).thenReturn(300);

        when(config.couriers()).thenReturn(List.of(courier1, courier2));

        pizzeria = new Pizzeria(config);
    }

    @Test
    void testPizzeriaConfiguration() {
        assertDoesNotThrow(() -> pizzeria.configure());
    }

    @Test
    void testPizzeriaConfigurationWithInvalidCapacity() {
        when(config.warehouseCapacity()).thenReturn(-1);

        assertThrows(ConfigurationLoadingException.class, () -> pizzeria.configure());
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testFullPizzeriaSimulation() {
        pizzeria.configure();

        assertDoesNotThrow(() -> pizzeria.startSimulate());
    }

    @Test
    void testMultiplePizzeriaStarts() {
        pizzeria.configure();


        pizzeria.startSimulate();


        Pizzeria pizzeria2 = new Pizzeria(config);
        pizzeria2.configure();

        assertDoesNotThrow(() -> pizzeria2.startSimulate());
    }

    @Test
    void testShutdownWithInterruptedMain() throws InterruptedException {
        pizzeria.configure();

        Thread mainThread = new Thread(() -> pizzeria.startSimulate());
        mainThread.start();

        Thread.sleep(500);
        mainThread.interrupt();
        mainThread.join(3000);

        assertFalse(mainThread.isAlive());
    }
}