
public class Card {
	private String suit; //Spades, hearts, diamonds, clubs

						//ENUMERATED SUITS:
						//SPADES = 0
						//HEARTS = 1
						//DIAMONDS =2
						//CLUBS = 3


	private int value; //from 1-13

	//CONSTRUCTOR:
	Card(String theSuit, int theValue){
		this.suit = theSuit;
		this.value = theValue;
	}//end Constructor

	//card overload constructor with suit as int value
	//for easy initialization
	Card(int suit, int theValue){
		if (suit == 0) {
			this.suit = "Spades";
		}
		else if (suit == 1) {
			this.suit = "Hearts";
		}
		else if (suit == 2) {
			this.suit = "Diamonds";
		}
		else if (suit == 3) {
			this.suit = "Clubs";
		}
		this.value = theValue;

	}//end Constructor

	public int getValue() {
		return value;
	}//end getValue

	public int getWorth() {
		//if the card is a 10 or face card it's worth zero points
		if (value >= 10 ) {
			return 0;
		}
		//if the card is an ace it's worth 1 point
		else return value;
	} //end getWorth


	public String getSuit() {
		return suit;
	}

	public String getValueAsString() {
		switch(value) {
		case 1:  return "Ace";
		case 2:  return "2";
		case 3:  return "3";
		case 4:  return "4";
		case 5:  return "5";
		case 6:  return "6";
		case 7:  return "7";
		case 8:  return "8";
		case 9:  return "9";
		case 10: return "10";
		case 11: return "Jack";
		case 12: return "Queen";
		case 13: return "King";
		default: return "Not a card";

		}
	}

}
