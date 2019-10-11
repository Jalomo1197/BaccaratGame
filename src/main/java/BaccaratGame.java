import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;

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
		//check if for 8 or 9s, To check for proper win.
		int playerTotal = gameLogic.handTotal(playerHand);
		int bankerTotal = gameLogic.handTotal(bankerHand);
		boolean pNaturalWin = (playerTotal == 9 || playerTotal == 8);
		boolean bNaturalWin = (bankerTotal == 9 || bankerTotal == 8);

		if (winner == "Player" && pNaturalWin)
			return currentBet*2;
		if (winner == "Banker" && bNaturalWin)
			return (0 - currentBet);
		else if (winner == "Draw" && pNaturalWin && bNaturalWin)
			return currentBet;

		//else we have to add cards to Hands
		Card newCard = null; //null
		//Player goes first
		if (gameLogic.evaluatePlayerDraw(playerHand)){ //If player does get another card
			//size check was done at dealing, so safe to execute.
			newCard = theDealer.drawOne();
			playerHand.add(newCard);
		}
		//Banker goes second
		if (gameLogic.evaluateBankerDraw(bankerHand, newCard)){
			newCard = theDealer.drawOne();
			bankerHand.add(newCard);
		}

		//call whoWon again
		winner = gameLogic.whoWon(playerHand, bankerHand);
		//return the amount won or lost based on the value in currentBet.
		if (winner == "Player")
			return currentBet*2;
		if (winner == "Banker")
			return (0 - currentBet);

		//else it was a draw and return bids
		return currentBet;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Image img = new Image("file:.../test/resources/UML.png");
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
		Image img = new Image("file:.../test/resources/UML.png");
		ImageView imv = new ImageView(img);

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
