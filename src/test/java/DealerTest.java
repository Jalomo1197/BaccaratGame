import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//testing DEALER class
class DealerTest {
	
	BaccaratDealer theDealer;
	
	@BeforeEach
	void init() {
		theDealer = new BaccaratDealer();
		theDealer.generateDeck();
	}
	
	
	@Test
	void testDeckSize() {
		assertEquals(52, theDealer.deckSize(), "Initial size of deck is not 52 cards");
		theDealer.drawOne();
		assertEquals(51, theDealer.deckSize(), "Draw one doesnt properly decrement deck size");	
		theDealer.drawOne();
		theDealer.drawOne();
		theDealer.drawOne();
		theDealer.drawOne();
		theDealer.drawOne();
		theDealer.drawOne();
		assertEquals(45, theDealer.deckSize(), "Draw one doesnt properly decrement deck size");			
	}
	
	@Test
	void testDealHand() {
		ArrayList<Card> c = theDealer.dealHand();
		assertEquals(50, theDealer.deckSize(), "Deal hand doesnt properly deal 2 cards");
		assertEquals("Card", c.get(0).getClass().getName(),"dealHand() not returning correct type (card) inside array list");
		assertEquals(2, c.size(), "Deal hand doesn't properly put two cards into the return ArrayList");
		//checking if dealHand() properly removes the cards dealt from the deck 
		for (Card pick: c) {
			assertEquals(false, theDealer.deck.contains(pick), "Dealing a hand doesnt properly remove it from deck.");
		}
	}
	
	@Test
	void testShuffleDeck() {
		//creating two instances of a dealer and generate a deck for both
		//they should be in the same order based on how we initialized them 
		BaccaratDealer test = new BaccaratDealer();
		test.generateDeck();
		
		assertEquals(false, test.deck.equals(theDealer.deck), "Decks arent properly shuffling" );
		
		test.dealHand();
		test.shuffleDeck();
		assertEquals(50, test.deckSize(), "Shuffling deck shouldnt change the size of the deck");
	}
	
	@Test
	void testDrawOne() {
		Card c= theDealer.drawOne();
		assertEquals(false, theDealer.deck.contains(c), "Draw one isn't properly removing a card from the deck");

	}

}
