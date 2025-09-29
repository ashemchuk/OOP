package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ashemchuk.deck.Deck;
import ru.ashemchuk.game.Game;
import ru.ashemchuk.game.Output;
import ru.ashemchuk.player.Dealer;
import ru.ashemchuk.player.Hand;
import ru.ashemchuk.player.User;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Mock
    private Deck mockDeck;

    @Mock
    private Dealer mockDealer;


    @Mock
    private User mockUser;

    @Mock
    private Hand mockUserHand;

    @Mock
    private Hand mockDealerHand;

    @Mock
    private Output mockOutput;

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(mockDeck, mockDealer, mockUser, mockOutput);
    }

    @Test
    void testGetRound_InitialRound() {
        assertEquals(1, game.getRound());
    }

    @Test
    void testRunGame_UserWinsAfterThreeRounds() {
        when(mockUser.getScore()).thenReturn(0, 1, 2, 3);
        when(mockDealer.getScore()).thenReturn(0, 0, 1, 1);

        Game spyGame = spy(game);
        doNothing().when(spyGame).round();

        spyGame.runGame();

        verify(spyGame, times(3)).round();
    }

    @Test
    void testRunGame_DealerWinsAfterThreeRounds() {
        when(mockUser.getScore()).thenReturn(0, 1, 1);
        when(mockDealer.getScore()).thenReturn(0, 1, 2, 3);

        Game spyGame = spy(game);
        doNothing().when(spyGame).round();

        spyGame.runGame();

        verify(spyGame, times(3)).round();
    }

    @Test
    void testRound_UserBusts() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(22);

        game.round();

        verify(mockDealer, never()).turn(mockDeck);
        verify(mockOutput).printRoundStart(game);
        verify(mockOutput, times(2)).printHand(mockUser);
        verify(mockOutput, times(2)).printHand(mockDealer);
        verify(mockOutput).printTurn(mockUser);
        verify(mockOutput).printWinner(mockUser, mockDealer);
        verify(mockOutput).printScores(mockUser, mockDealer);

        verify(mockUserHand).clear();
        verify(mockDealerHand).clear();
    }

    @Test
    void testRound_DealerPlaysWhenUserUnder21() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(20);
        when(mockDealer.turn(mockDeck)).thenReturn(false);

        game.round();

        verify(mockDealer).openHoleCard();
        verify(mockDealer).turn(mockDeck);
        verify(mockOutput).printTurn(mockDealer);
    }

    @Test
    void testRound_CardsAreClearedAfterRound() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(18);
        when(mockDealer.turn(mockDeck)).thenReturn(false);

        game.round();

        verify(mockUserHand).clear();
        verify(mockDealerHand).clear();
        assertEquals(2, game.getRound());
    }

    @Test
    void testRound_UserTakesMultipleCards() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(15, 18, 20);
        when(mockUser.turn(mockDeck)).thenReturn(true, true, false);
        when(mockDealer.turn(mockDeck)).thenReturn(false);

        game.round();

        verify(mockUser, times(3)).turn(mockDeck);
        verify(mockDealer).openHoleCard();
    }

    @Test
    void testRound_DealerOpensHoleCard() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(17);
        when(mockDealer.turn(mockDeck)).thenReturn(false);

        game.round();

        verify(mockDealer).openHoleCard();
    }

    @Test
    void testRound_DealerTakesCardsWhenNeeded() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(18);
        when(mockDealer.turn(mockDeck)).thenReturn(true, true, false);

        game.round();

        verify(mockDealer, times(3)).turn(mockDeck);
    }

    @Test
    void testRound_InitialCardDistribution() {
        when(mockUser.getHand()).thenReturn(mockUserHand);
        when(mockDealer.getHand()).thenReturn(mockDealerHand);

        when(mockUserHand.getTotalWorth()).thenReturn(18);
        when(mockDealer.turn(mockDeck)).thenReturn(false);

        game.round();

        verify(mockDealer).takeCard(mockDeck);
        verify(mockDealer).takeCard(mockDeck, true);
        verify(mockUser, times(2)).takeCard(mockDeck);
    }
}