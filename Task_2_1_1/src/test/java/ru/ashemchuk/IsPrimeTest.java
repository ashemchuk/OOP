package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ashemchuk.IsPrime.isPrime;

import org.junit.jupiter.api.Test;

class IsPrimeTest {

    @Test
    void test() {
        assertFalse(isPrime(0));
        assertFalse(isPrime(1));
        assertFalse(isPrime(-256));
        assertFalse(isPrime(256));

        assertTrue(isPrime(13));
    }
}