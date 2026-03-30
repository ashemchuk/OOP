package ru.ashemchuk.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import ru.ashemchuk.queue.order.Order;
import ru.ashemchuk.queue.order.OrderState;

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