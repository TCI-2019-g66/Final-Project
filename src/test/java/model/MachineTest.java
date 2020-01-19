package model;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MachineTest {

    @Test
    public void createMachine_Always_ShouldPass() {
        // arrange
        Machine machine = new Machine();
        //assert
        assertTrue("Machine is created successfully", machine != null);
    }

    @Test
    public void connectCard_ShouldPass() {
        // arrange
        Card card = mock(Card.class);
        Machine machine = new Machine();
        // act
        machine.connectCard(card);
        // assert
        assertTrue("Card should be stored in the connected Cards list", machine.getConnectedCards().contains(card));
    }

    @Test
    public void connectMultipleCards_ShouldPass() {
        // arrange
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);
        Machine machine = new Machine();
        // act
        machine.connectCard(card1);
        machine.connectCard(card2);
        // assert
        assertTrue("Both cards should be stored in the connected Cards list", machine.getConnectedCards().contains(card1) && machine.getConnectedCards().contains(card2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void connectCard_WithNullCard_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        // act
        machine.connectCard(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void connectCard_WithAlreadyConnectedCard_ShouldThrowIllegalArgumentsException() {
        // arrange
        Card card = mock(Card.class);
        Machine machine = new Machine();
        // act
        machine.connectCard(card);
        machine.connectCard(card);
    }

    @Test
    public void disconnectCard_ShouldPass() {
        // arrange
        Card card = mock(Card.class);
        Machine machine = new Machine();
        machine.connectCard(card);
        // act
        machine.disconnectCard(card);
        // assert
        assertTrue("Card should be removed from the connected Cards list", !machine.getConnectedCards().contains(card));


    }

    @Test
    public void disconnectMultipleCards_ShouldPass() {
        // arrange
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);
        Machine machine = new Machine();
        machine.connectCard(card1);
        machine.connectCard(card2);
        // act
        machine.disconnectCard(card2);
        machine.disconnectCard(card1);
        // assert
        assertTrue("Both cards should be removed from the connected Cards list", !machine.getConnectedCards().contains(card1) && !machine.getConnectedCards().contains(card2));

    }

    @Test (expected = IllegalArgumentException.class)
    public void disconnectCard_WithNullCard_ShouldThrowIllegalArgumentsException() {
        // arrange
        Card card = mock(Card.class);
        Machine machine = new Machine();
        machine.connectCard(card);
        // act
        machine.disconnectCard(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void disconnectCard_WithNotConnectedCard_ShouldThrowIllegalArgumentsException() {
        // arrange
        Card card = mock(Card.class);
        Machine machine = new Machine();
        // act
        machine.disconnectCard(card);
    }

    @Test
    public void givePrize_ShouldPass() {
        // arrange
        Machine machine = new Machine();
        Bet bet = mock(Bet.class);
        Card card = mock(Card.class);
        when(bet.isResolved()).thenReturn(true);
        when(bet.getCard()).thenReturn(card);
        // act
        machine.givePrize(bet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void givePrize_WithNullBet_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        // act
        machine.givePrize(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void givePrize_WithUnresolvedBet_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        Bet bet = mock(Bet.class);
        Card card = mock(Card.class);
        when(bet.isResolved()).thenReturn(false);
        when(bet.getCard()).thenReturn(card);
        // act
        machine.givePrize(bet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeBet_WithNegativeInValue_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        machine.connectCard(card);
        // act
        machine.placeBet(card, -1.0);
    }

    @Test
    public void placeBet_WithPositiveInValue_ShoudlPass() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        machine.connectCard(card);
        // act
        machine.placeBet(card, 1.0);
    }

    @Test
    public void placeBet_WithZeroInValue_ShouldPass() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        machine.connectCard(card);
        // act
        machine.placeBet(card, 0.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void placeBet_WithNullInValue_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        machine.connectCard(card);
        // act
        machine.placeBet(card, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void placeBet_WithNotConnectedCard_ShouldThrowIllegalArgumentsException() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        // act
        machine.placeBet(card, 1.0);
    }

    @Test (expected = InvalidBettingRoundException.class)
    public void placeBet_WithNoCurrentBettingRound_ShouldThrowInvalidBettingRoundException() {
        // arrange
        Machine machine = new Machine();
        Card card = mock(Card.class);
        Game game = mock(Game.class);
        machine.setGame(game);
        machine.connectCard(card);
        // act
        machine.placeBet(card, 1.0);
        machine.placeBet(card, 0.0);
        machine.placeBet(card, 0.0);
        when(game.getCurrentBettingRound()).thenReturn(null);
        machine.placeBet(card, 1.0);
    }
}