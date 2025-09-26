package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DealerTest {

    @Test
    void takeCard() {
        Dealer dealer = new Dealer(new Hand());
        Deck deck = new Deck();
        assertNull(dealer.getHoleCard());
        dealer.takeCard(deck, false);
        assertNull(dealer.getHoleCard());
        dealer.takeCard(deck, true);
        assertNotNull(dealer.getHoleCard());
    }
}