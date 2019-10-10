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

	public double evaluateWinnings() {
		//This method will determine
		//if the user won or lost their bet and
		//return the amount won or lost based on the value in currentBet.

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

			//add it to the players hand
			//we keep a local copy to pass to the evaluateBankerDraw
		}



		return 0;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
