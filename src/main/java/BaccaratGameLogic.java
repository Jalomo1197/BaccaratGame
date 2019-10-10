import java.util.ArrayList;

public class BaccaratGameLogic {
	public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand) {
		//The method whoWon will evaluate two hands at the end of the game
		//and return a string depending on the winner: “Player”, “Banker”, “Draw”.
	    //ASSUMING hand1 is the player and hand2 is the Banker
		//Note: how do we handle if both players have a winning hand?
		int playerTotal = handTotal(playerHand);
		int bankerTotal = handTotal(bankerHand);
		if (playerTotal == 8 || playerTotal == 9) {
			if (bankerTotal == 8 || bankerTotal == 9)
				return "Draw";
			return "Player";
		}
		else if (bankerTotal == 8 || bankerTotal == 9) {
			return "Banker";
		}
		else
		//closest to 9
			//player win
			//baker
		else return "Draw";

	}
	public int handTotal(ArrayList<Card> hand) {
		//take a hand and return how many points that hand is worth.
		//iterate through hand ArrayList
		int handTotal = 0;
		for(Card c: hand)
			handTotal += c.getWorth();

		if (handTotal > 9)
				handTotal %= 10;

		return handTotal;
	}
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		//return true if Banker should be dealt a third card, otherwise return false.
		int total = handTotal(hand);
		int
		if (total > 6) {
			return false;
		}
		else if (total < 3) {
			return true;
		}
		else {

			//idk see pdf
		}
		return true;
	}


	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		//return true if Player should be dealt a third card, otherwise return false.
		int tot = handTotal(hand);

		if (tot <= 5)
			return true;
		else
			return false;
	}

}
