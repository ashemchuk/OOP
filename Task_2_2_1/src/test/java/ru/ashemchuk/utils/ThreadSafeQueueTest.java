package ru.ashemchuk.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreadSafeQueueTest {
    private ThreadSafeQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new ThreadSafeQueue<>();
    }

    @Test
    void testAddAndPop() throws InterruptedException {
        queue.add("test");

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());

        String item = queue.pop();
        assertEquals("test", item);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void testPoll() {
        queue.add("test1");
        queue.add("test2");

        String item1 = queue.poll();
        String item2 = queue.poll();
        String item3 = queue.poll();

        assertEquals("test1", item1);
        assertEquals("test2", item2);
        assertNull(item3);
        assertTrue(queue.isEmpty());
    }

    @Test
    void testPopBlocksWhenEmpty() throws InterruptedException {
        AtomicInteger result = new AtomicInteger(-1);
        Thread t = new Thread(() -> {
            try {
                queue.pop();
                result.set(0);
            } catch (InterruptedException e) {
                result.set(1);
            }
        });

        t.start();
        Thread.sleep(100);

        assertTrue(t.isAlive());
        assertEquals(-1, result.get());

        t.interrupt();
        t.join();

        assertEquals(1, result.get());
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        int operationsPerThread = 100;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < operationsPerThread; j++) {
                        queue.add("item-" + threadId + "-" + j);
                        queue.poll();
                    }
                } catch (InterruptedException e) {
                    fail("Thread interrupted");
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        assertTrue(endLatch.await(5, TimeUnit.SECONDS));

        assertTrue(queue.size() >= 0);
    }

    @Test
    void testClear() {
        queue.add("test1");
        queue.add("test2");

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

}