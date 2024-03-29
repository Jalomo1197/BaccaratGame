import java.util.ArrayList;

public class BaccaratGameLogic {


	public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand) {
		//The method whoWon will evaluate two hands at the end of the game
		//and return a string depending on the winner: “Player”, “Banker”, “Draw”.
	    //ASSUMING hand1 is the player and hand2 is the Banker
		//Note: how do we handle if both players have a winning hand?
		int playerTotal = handTotal(playerHand);
		int bankerTotal = handTotal(bankerHand);
		//checking for natural win
		if (playerTotal == 8 || playerTotal == 9) {
			if (bankerTotal == 8 || bankerTotal == 9)
				return "Draw";
			return "Player";
		}
		else if (bankerTotal == 8 || bankerTotal == 9) {
			return "Banker";
		}

		//assigning the distance to 9 in these variables.
		playerTotal = 9 - playerTotal;
		bankerTotal = 9 - bankerTotal;
		//checking for draw
		if (playerTotal == bankerTotal)
			return "Draw";
		//returning who ever is closest to nine
		return (playerTotal < bankerTotal)? "Player" : "Banker";
	}


	public int handTotal(ArrayList<Card> hand) {
		//take a hand and return how many points that hand is worth.
		//iterate through hand ArrayList
		if (hand == null){
			System.out.println("NO OBJECT PASSED TO HANDTOTAL FUNCTION");
			return -1;
		}

		int handTotal = 0;
		for(Card c: hand){
			handTotal += c.getWorth();
		}
		if (handTotal > 9)
				handTotal %= 10;

		return handTotal;
	}


	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		//return true if Banker should be dealt a third card, otherwise return false.
		int total = handTotal(hand);
		int playerVal;
		if (total > 6) {
			return false;
		}
		else if (total < 3) {
			return true;
		}

		//else we know that it depends on the value of the player card, if any.
		if (playerCard == null && total != 6)
			return true;
		if (playerCard == null && total == 6)
			return false;

		playerVal = playerCard.getWorth();
		if (total == 3 && playerVal != 8)
			return true;

		if (total == 4 && playerVal > 1 && playerVal < 8)
			return true;

		if (total == 5 && playerVal > 3 && playerVal < 8)
			return true;

		if (total == 6 && playerVal > 5 && playerVal < 8)
			return true;

		return false;
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
