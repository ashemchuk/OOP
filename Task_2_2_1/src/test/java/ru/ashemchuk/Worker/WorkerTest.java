package ru.ashemchuk.Worker;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.OrderQueue.Order.Order;
import ru.ashemchuk.OrderQueue.Order.OrderState;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    @Test
    void testLogMethod() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TestWorker worker = new TestWorker();
        Order order = new Order(42, OrderState.WAITING);

        worker.log(order);

        String output = outContent.toString();
        assertTrue(output.contains("order 42"));
        assertTrue(output.contains("WAITING"));

        System.setOut(System.out);
    }

    @Test
    void testLogWithNull() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TestWorker worker = new TestWorker();
        worker.log(null);

        assertEquals("", outContent.toString());

        System.setOut(System.out);
    }

    @Test
    void testRunMethod() throws InterruptedException {
        TestWorker worker = new TestWorker();
        Thread workerThread = new Thread(worker);

        workerThread.start();
        Thread.sleep(100);
        workerThread.interrupt();
        workerThread.join();

        assertTrue(worker.workCalled);
    }

    private static class TestWorker extends Worker {
        boolean workCalled = false;

        @Override
        public void work() throws InterruptedException {
            workCalled = true;
            Thread.sleep(50);
        }
    }
}