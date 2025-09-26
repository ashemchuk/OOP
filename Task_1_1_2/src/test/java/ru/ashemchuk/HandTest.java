package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

    @Test
    void getTotalWorth() {
        Hand hand = new Hand();
        assertEquals(0, hand.getTotalWorth());
        Card ace = new Card(Suit.DIAMOND, Rank.ACE);
        hand.addCard(ace);
        assertEquals(1, hand.getCards().size());
        assertEquals(11, hand.getTotalWorth());
        ace.getRank().downgrade();
        assertEquals(1, hand.getTotalWorth());
        hand.clear();
        assertEquals(0, hand.getCards().size());
    }
}