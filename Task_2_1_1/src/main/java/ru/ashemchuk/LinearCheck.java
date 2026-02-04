package ru.ashemchuk;

public class LinearCheck implements Checker{
    @Override
    public boolean isAllPrime(int[] nums) {
        for (var n : nums) {
            if (!IsPrime.isPrime(n)) {
                return true;
            }
        }
        return  false;
    }
}
