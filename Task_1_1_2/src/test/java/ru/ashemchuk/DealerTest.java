package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DealerTest {

    @Test
    void takeNormalCard() {
        Dealer dealer = new Dealer(new Hand());
        assertEquals("Dealer", dealer.getTitle());
        assertEquals(0, dealer.getScore());

        Deck deck = new Deck();
        assertNull(dealer.getHoleCard());

        dealer.takeCard(deck, false);
        assertNull(dealer.getHoleCard());

        dealer.takeCard(deck, true);
        assertNotNull(dealer.getHoleCard());

        dealer.addScore(1);
        assertEquals(1, dealer.getScore());
    }

    @Test
    void takeAce() {
        Hand hand = Mockito.mock(Hand.class);
        Mockito.when(hand.getTotalWorth()).thenReturn(22);

        Dealer dealer = new Dealer(hand);

        Deck deck = Mockito.mock(Deck.class);
        Mockito.when(deck.pull()).thenReturn(new Card(Suit.DIAMOND, Rank.ACE));

        Card taken = dealer.takeCard(deck);
        Rank rank = taken.getRank();
        assertEquals(Rank.ACE_DOWNGRADED, rank);
        assertEquals(1, rank.getWorth());
    }
}