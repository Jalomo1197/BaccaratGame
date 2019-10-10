import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
	ArrayList<Card> deck;

	public void generateDeck() {
		//generate a new standard 52 card deck
		//where each card is an instance of the Card class in the ArrayList<Card> deck.
		deck = new ArrayList<Card>();
		for (int suit = 0; suit <= 3; suit++) {
			for (int val = 0; val <= 13; val++ ) {
				Card c = new Card(suit, val);
				deck.add(c);
			}
		}
	}

	public bool EnoughtCard(){
		return (this.deckSize() < 6)? false : true;
	}

	public ArrayList<Card> dealHand(){
		//will deal two cards and return them in an ArrayList<Card>
		Card c1 = deck.remove(0);
		Card c2 = deck.remove(0);  //MAY NOT WORK,
		ArrayList<Card> ret = new ArrayList<Card>();
		ret.add(c1);
		ret.add(c2);
		deck.remove(0);
		deck.remove(1);
		return ret;
	}

	public Card drawOne() {
		//drawOne will deal a single card and return it.
		Card c = deck.get(0);
		deck.remove(0);
		return c;

	}
	public void shuffleDeck() {
		//will create a new deck of 52 cards and “shuffle”
		//randomize the cards in that ArrayList<Card>
		Collections.shuffle(deck);
	}
	public int deckSize() {
		//will just return how many cards are in this.deck at any given time.
		return deck.size();
	}

}
