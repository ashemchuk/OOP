package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.deck.card.Card;
import ru.ashemchuk.deck.card.Rank;
import ru.ashemchuk.deck.card.Suit;
import ru.ashemchuk.player.Hand;

class HandTest {

    @Test
    void getTotalWorth() {
        Hand hand = new Hand();
        assertEquals(0, hand.getTotalWorth());

        Card ace = new Card(Suit.DIAMOND, Rank.ACE);
        hand.addCard(ace);
        assertEquals("[Diamond of Ace(11)] => 11", hand.toString());
        assertEquals(1, hand.getCards().size());
        assertEquals(11, hand.getTotalWorth());

        ace.downgrade();
        assertEquals(1, hand.getTotalWorth());

        hand.clear();
        assertEquals(0, hand.getCards().size());

        hand.addCard(ace.withHole());
        assertEquals("[<hole card>]", hand.toString());

        hand.getCards().get(0).open();
        assertEquals("[Diamond of Ace(1)] => 1", hand.toString());
    }
}