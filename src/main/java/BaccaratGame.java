
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import java.io.FileInputStream;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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

		//generate deck
		//check for size (atleast six)
			//regenerate if needed
		//deal hands
		//call evaluateWinnings


		launch(args);
	}

	//GUI STUFF
	
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		HashMap<String, Scene> sceneMap; 

		
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primaryStage.setTitle("Let's Play Baccarat!!!");
		Scene scene = createStartScene();
		primaryStage.setScene(scene);
		primaryStage.show();
	} //end start function
	
	public Scene createGameScene() {
		
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
		Scene s = new Scene(layout);
		return s;
	}
	
	//method to create our first scene with controls
	public Scene createStartScene() {
		
	
		Image pic = new Image("file:src/test/resources/PlayButton.png");
		ImageView v = new ImageView(pic);
		//v.setCache(true);
		//v.setFitHeight(146);
		//v.setFitWidth(470);
		//v.setPreserveRatio(true);
		Button playButton = new Button();
		//playButton.setOnAction(returnButtons);
		playButton.setGraphic(v);
		playButton.setStyle("-fx-background-color:black;");

		VBox startBox = new VBox(playButton);
		//startBox.setPadding(new Insets(70));
		//startBox.setSpacing(10);
		//TextField title = new TextField("stuff");	
		startBox.setStyle("-fx-background-color:black;");
		
		
		return new Scene(startBox,700, 400);
		
	}//end createStartScene

	

}
