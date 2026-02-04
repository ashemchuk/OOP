package ru.ashemchuk;

import static ru.ashemchuk.IsPrime.isPrime;

import java.util.Arrays;


public class ParallelCheck implements Checker{
    @Override
    public boolean isAllPrime(int[] n) {
        return Arrays.stream(n).parallel().anyMatch(a -> !isPrime(a));
    }
}
