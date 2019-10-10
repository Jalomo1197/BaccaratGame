import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;
	String winner; //need to pass winner info accross functions e.g. evaluateWinnings()

	public double evaluateWinnings() {
		//This method will determine
		//if the user won or lost their bet
		//call whowon
		winner = gameLogic.whoWon(playerHand, bankerHand);
		//check if for 8 or 9s in
		int playerTotal = gameLogic.handTotal(playerHand);
		int bankerTotal = gameLogic.handTotal(bankerHand)
		if (winner == "Player" && (playerTotal == 9 || playerTotal == 8)){

		}
		if (winner == "Banker")
		else
				//player if player won
				//banker if bank won
				//both is draw

		//else we have to add cards to hands in
		Card newCard; //null
		if (evaluatePlayerDraw(playerHand)){ //If player does get another card
			//size check was done at dealing, so safe to execute.
			//draw one
			newCard = theDealer.drawOne();
			playerHand.add(newCard);
			if (evaluateBankerDraw(bankerHand, newCard)){
				newCard = theDealer.drawOne();
				bankerHand.add(newCard);
			}
		}


		//call whoWon again

		//return the amount won or lost based on the value in currentBet.
		return 0;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//generate deck
		//check for size (atleast six)
			//regenerate if needed
		//deal hands
		//call evaluateWinnings


		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Button player = new Button("PLAYER");
		Button banker = new Button("BANKER");
		Button draw = new Button("DRAW");
		TextField text = new TextField();

		player.setOnAction(e->text.setText("Player"));
		banker.setOnAction(e->text.setText("banker"));
		draw.setOnAction(e->text.setText("draw"));


		BorderPane layout = new BorderPane();
		layout.getChildren().addAll(player, banker, draw);
		primaryStage.setTitle("Let's Play Baccarat!!!");
		Scene scene = new Scene(layout,600,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
