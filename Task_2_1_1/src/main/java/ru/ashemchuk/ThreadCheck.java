package ru.ashemchuk;

import static ru.ashemchuk.IsPrime.isPrime;

import java.util.ArrayList;
import java.util.List;

public class ThreadCheck implements Checker{
    @Override
    public boolean isAllPrime(int[] n) {
        return isAllPrime(n, Runtime.getRuntime().availableProcessors());
    }
    private static volatile boolean ans = false;
    public boolean isAllPrime(int[] n, int threadsCount) {
        List<Thread> threads = new ArrayList<>();
        int step = n.length / threadsCount;
        for (int i = 0; i < threadsCount; i++) {
            int start = i * step;
            int end = i == threadsCount - 1 ? step * ( i + 1) + n.length % threadsCount : step * (i + 1);

            Thread t = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(n[j])) {
                        ans = true;
                        break;
                    }
                }
            });
            threads.add(t);
        }

        for (var t: threads) {
            t.start();
        }
        return ans;
    }
}
