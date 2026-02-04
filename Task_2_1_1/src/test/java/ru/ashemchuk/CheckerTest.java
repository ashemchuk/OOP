package ru.ashemchuk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    private LinearCheck linearCheck;
    private ParallelCheck parallelCheck;
    private ThreadCheck threadCheck;

    @BeforeEach
    void setUp() {
        linearCheck = new LinearCheck();
        parallelCheck = new ParallelCheck();
        threadCheck = new ThreadCheck();
    }

    @Test
    void testAllPrimeNumbers() {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        assertFalse(linearCheck.isAllPrime(primes));
        assertFalse(parallelCheck.isAllPrime(primes));
        assertFalse(threadCheck.isAllPrime(primes));
    }

    @Test
    void testAllNonPrimeNumbers() {
        int[] nonPrimes = {1, 4, 6, 8, 9, 10, 12, 14, 15, 16};
        assertTrue(linearCheck.isAllPrime(nonPrimes));
        assertTrue(parallelCheck.isAllPrime(nonPrimes));
        assertTrue(threadCheck.isAllPrime(nonPrimes));
    }

    @Test
    void testMixedNumbersWithNonPrimeFirst() {
        int[] mixed = {4, 5, 7, 11, 13};
        assertTrue(linearCheck.isAllPrime(mixed));
        assertTrue(parallelCheck.isAllPrime(mixed));
        assertTrue(threadCheck.isAllPrime(mixed));
    }

    @Test
    void testMixedNumbersWithNonPrimeLast() {
        int[] mixed = {2, 3, 5, 7, 12};
        assertTrue(linearCheck.isAllPrime(mixed));
        assertTrue(parallelCheck.isAllPrime(mixed));
        assertTrue(threadCheck.isAllPrime(mixed));
    }

    @Test
    void testSinglePrimeNumber() {
        int[] singlePrime = {13};
        assertFalse(linearCheck.isAllPrime(singlePrime));
        assertFalse(parallelCheck.isAllPrime(singlePrime));
        assertFalse(threadCheck.isAllPrime(singlePrime));
    }

    @Test
    void testSingleNonPrimeNumber() {
        int[] singleNonPrime = {1};
        assertTrue(linearCheck.isAllPrime(singleNonPrime));
        assertTrue(parallelCheck.isAllPrime(singleNonPrime));
        assertTrue(threadCheck.isAllPrime(singleNonPrime));
    }

    @Test
    void testEmptyArray() {
        int[] empty = {};
        assertFalse(linearCheck.isAllPrime(empty));
        assertFalse(parallelCheck.isAllPrime(empty));
        assertFalse(threadCheck.isAllPrime(empty));
    }

    @Test
    void testLargeArray() {
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = 2;
        }

        assertFalse(linearCheck.isAllPrime(largeArray));
        assertFalse(parallelCheck.isAllPrime(largeArray));
        assertFalse(threadCheck.isAllPrime(largeArray));
    }

    @Test
    void testLargeArrayWithNonPrime() {
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = 2;
        }
        largeArray[5000] = 4;

        assertTrue(linearCheck.isAllPrime(largeArray));
        assertTrue(parallelCheck.isAllPrime(largeArray));
        assertTrue(threadCheck.isAllPrime(largeArray));
    }

    @Test
    void testBoundaryValues() {
        int[] boundary = {0, 1, 2, 3, 4, 5, 6};
        assertTrue(linearCheck.isAllPrime(boundary));
        assertTrue(parallelCheck.isAllPrime(boundary));
        assertTrue(threadCheck.isAllPrime(boundary));
    }

    @Test
    void testNegativeNumbers() {
        int[] negatives = {-5, -1, 0, 1, 2, 3};
        assertTrue(linearCheck.isAllPrime(negatives));
        assertTrue(parallelCheck.isAllPrime(negatives));
        assertTrue(threadCheck.isAllPrime(negatives));
    }

    @Test
    void testIsPrimeMethod() {
        assertFalse(IsPrime.isPrime(0));
        assertFalse(IsPrime.isPrime(1));
        assertTrue(IsPrime.isPrime(2));
        assertTrue(IsPrime.isPrime(3));
        assertFalse(IsPrime.isPrime(4));
        assertTrue(IsPrime.isPrime(5));
        assertFalse(IsPrime.isPrime(9));
        assertTrue(IsPrime.isPrime(13));
        assertFalse(IsPrime.isPrime(15));
        assertTrue(IsPrime.isPrime(17));
        assertFalse(IsPrime.isPrime(25));
        assertTrue(IsPrime.isPrime(29));
        assertFalse(IsPrime.isPrime(-5));
    }
}