package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.ashemchuk.deck.card.Card;
import ru.ashemchuk.game.Game;
import ru.ashemchuk.game.Output;
import ru.ashemchuk.player.Hand;
import ru.ashemchuk.player.Player;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OutputTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Output output;
    @Mock
    private Game mockGame;

    @Mock
    private Player mockPlayer1;

    @Mock
    private Player mockPlayer2;

    @Mock
    private Hand mockHand1;

    @Mock
    private Hand mockHand2;

    @Mock
    private Card mockCard;

    @BeforeEach
    void setUp() {
        output = new Output();
        System.setOut(new PrintStream(outContent));

        when(mockPlayer1.getTitle()).thenReturn("Player1");
        when(mockPlayer2.getTitle()).thenReturn("Player2");
        when(mockPlayer1.getHand()).thenReturn(mockHand1);
        when(mockPlayer2.getHand()).thenReturn(mockHand2);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testPrintRoundStart() {
        when(mockGame.getRound()).thenReturn(3);
        output.printRoundStart(mockGame);

        String expectedOutput = "Round 3" + System.lineSeparator()
            + "Dealer dealt the cards" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintHand() {
        when(mockHand1.toString()).thenReturn("[Ace of Spades, King of Hearts]");

        output.printHand(mockPlayer1);

        String expectedOutput = "\tPlayer1, your cards: [Ace of Spades, King of Hearts]"
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintInputPrompt() {
        output.printInputPrompt();

        String expectedOutput = "Enter \"1\" to take a card and \"0\" to stop..."
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintMove() {
        when(mockCard.toString()).thenReturn("Ace of Spades");
        when(mockHand1.toString()).thenReturn("[Ace of Spades]");

        output.printMove(mockPlayer1, mockCard);

        String expectedOutput = "Player1 open card: Ace of Spades" + System.lineSeparator()
            + "\tPlayer1, your cards: [Ace of Spades]" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintTurn() {
        output.printTurn(mockPlayer1);

        String expectedOutput = "Player1, it's your turn!" + System.lineSeparator()
            + "----------------" + System.lineSeparator()
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintBadInput() {
        output.printBadInput();

        String expectedOutput = "Bad input:(" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintWinner_Draw() {
        when(mockHand1.getTotalWorth()).thenReturn(18);
        when(mockHand2.getTotalWorth()).thenReturn(18);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Ooops, it's draw" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1, never()).addScore(anyInt());
        verify(mockPlayer2, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player1Blackjack() {
        when(mockHand1.getTotalWorth()).thenReturn(21);
        when(mockHand2.getTotalWorth()).thenReturn(18);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "BLACKJACK! Player1, you won" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1).addScore(1);
        verify(mockPlayer2, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player2Blackjack() {
        when(mockHand1.getTotalWorth()).thenReturn(18);
        when(mockHand2.getTotalWorth()).thenReturn(21);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "BLACKJACK! Player2, you won" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer2).addScore(1);
        verify(mockPlayer1, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player2Busts() {
        when(mockHand1.getTotalWorth()).thenReturn(18);
        when(mockHand2.getTotalWorth()).thenReturn(22);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player1, you won! Player2 has over than 21 points"
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1).addScore(1);
        verify(mockPlayer2, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player1Busts() {
        when(mockHand1.getTotalWorth()).thenReturn(23);
        when(mockHand2.getTotalWorth()).thenReturn(18);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player2, you won! Player1 has over than 21 points"
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer2).addScore(1);
        verify(mockPlayer1, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player1WinsNormal() {
        when(mockHand1.getTotalWorth()).thenReturn(19);
        when(mockHand2.getTotalWorth()).thenReturn(18);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player1, you won!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1).addScore(1);
        verify(mockPlayer2, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_Player2WinsNormal() {
        when(mockHand1.getTotalWorth()).thenReturn(17);
        when(mockHand2.getTotalWorth()).thenReturn(19);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player2, you won!" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer2).addScore(1);
        verify(mockPlayer1, never()).addScore(anyInt());
    }

    @Test
    void testPrintWinner_BothPlayersBust() {
        when(mockHand1.getTotalWorth()).thenReturn(23);
        when(mockHand2.getTotalWorth()).thenReturn(22);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player1, you won! Player2 has over than 21 points"
            + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1).addScore(1);
        verify(mockPlayer2, never()).addScore(anyInt());
    }

    @Test
    void testPrintScores() {
        when(mockPlayer1.getScore()).thenReturn(2);
        when(mockPlayer2.getScore()).thenReturn(1);

        output.printScores(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player1 2:1 Player2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintScores_ZeroScores() {
        when(mockPlayer1.getScore()).thenReturn(0);
        when(mockPlayer2.getScore()).thenReturn(0);

        output.printScores(mockPlayer1, mockPlayer2);

        String expectedOutput = "Player1 0:0 Player2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testPrintWinner_EdgeCase21vs20() {
        when(mockHand1.getTotalWorth()).thenReturn(21);
        when(mockHand2.getTotalWorth()).thenReturn(20);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "BLACKJACK! Player1, you won" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer1).addScore(1);
    }

    @Test
    void testPrintWinner_EdgeCase20vs21() {
        when(mockHand1.getTotalWorth()).thenReturn(20);
        when(mockHand2.getTotalWorth()).thenReturn(21);

        output.printWinner(mockPlayer1, mockPlayer2);

        String expectedOutput = "BLACKJACK! Player2, you won" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
        verify(mockPlayer2).addScore(1);
    }
}