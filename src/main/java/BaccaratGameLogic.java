import java.util.ArrayList;

public class BaccaratGameLogic {
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		//The method whoWon will evaluate two hands at the end of the game 
		//and return a string depending on the winner: “Player”, “Banker”, “Draw”.
	    //ASSUMING hand1 is the player and hand2 is the Banker
		//Note: how do we handle if both players have a winning hand?
		if (handTotal(hand1) == 8 || handTotal(hand1) == 9) {
			return "Player";
		}
		else if (handTotal(hand2) == 8 || handTotal(hand2) == 9) {
			return "Banker";
		}
		else return "Draw";
		
	}
	public int handTotal(ArrayList<Card> hand) {
		//take a hand and return how many points that hand is worth.
		//iterate through hand ArrayList 
		int handTotal = 0;
		for(Card c: hand) {
			handTotal += c.getWorth();
		}
		return handTotal;
	}
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		//return true if Banker should be dealt a third card, otherwise return false.
		int tot = handTotal(hand);
		if (tot >= 7) {
			return false;
		}
		else if (tot <= 2) {
			return true;
		}
		else {
			//idk		
		}
		return true;
	}
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		//return true if Player should be dealt a third card, otherwise return false.
		int tot = handTotal(hand);
		
		if (tot <= 5) {
			return true;
		}
		else if (tot == 6 || tot == 7) {
			return false;
		}
		//if the total is greater than 9 remove the first number from the total
		else if (tot> 9) {
			//remove first number?
			return false;
		}
		return false;
		
	}
	
	
}
