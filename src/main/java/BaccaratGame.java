import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;
import java.io.FileInputStream;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.*;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.application.Platform;

public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	ImageArrayList cardImages;
	//GUI STUFF
	EventHandler<ActionEvent> callSetScores, callHandTotal, showPlayAgainButton;
	BorderPane mainLayout;
	HBox player_cards, banker_cards;
	VBox hands;
	Timeline showWinner, finalScores, showPA;
  MenuItem refresh, exit;
	Font redFont = Font.font("Times New Roman", FontWeight.BOLD, 25);
	Text Money, betAmount, betChoice, playerScore, bankerScore, W, moneyInfo, betAmountInfo, betChoiceInfo, PlayerLabel, BankerLabel, betInstructions, spacer, spacer2, spacer3, spacer4;
	Button playButton, confirm, resetCurrentBet, playAgain, amount5, amount25, amount50, amount100, amount500;
	ImageView p1, p2, p3, b1, b2, b3, amount5img, v, v2;
	Image pic, pic2, img5;
	String s[] = {"Player", "Banker", "Draw"};
	String winner, bettingOn;
	ChoiceBox<String> c;
	int showMoneyChange_and_showWinner;
	double currentBet = 0;
	double totalWinnings = 0;


	//MAIN METHOD
	public static void main(String[] args) {
		launch(args);
	}


	//START METHOD
	@Override
	public void start(Stage primaryStage) throws Exception {
		//SETTING STAGE
		primaryStage.setWidth(850);
		primaryStage.setHeight(700);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Let's Play Baccarat!!!");
		//INITIALIZAION OF MANY MEMBERS
		initializeDataMembers();
		initializeImages_and_Imageviews();
		initializeButtons_and_setStyle();
		initializaTexts_and_setEffects();
		//SETTING SCENE
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		mainLayout.setCenter(startLayout());
		mainLayout.setStyle("-fx-background-color: #267617;");
		//EVENT HANDLERS TOO CALL FUNCTIONS IN KEYFRAMES/TIMELINE
		callSetScores = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				SetScores();
			}
		};

		callHandTotal = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				playerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(playerHand));
			}
		};

		showPlayAgainButton = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				HBox PA = new HBox(spacer4, playAgain);
				hands.getChildren().add(PA); //may want to delay this
				mainLayout.setRight(winnerLayout());
			}
		};

		finalScores = new Timeline(
			new KeyFrame(Duration.millis(9700),"B", callSetScores),
			new KeyFrame(Duration.millis(7700),"P", callHandTotal)
		);

		//PLAY BUTTON EVENT HANDLER
		playButton.setOnAction(e->{
			playButton.setGraphic(v2);
			mainLayout.setCenter(betLayout());
			mainLayout.setBottom(infoLayout());
			mainLayout.setTop(menuLayout());
			theDealer.generateDeck();
			theDealer.shuffleDeck();
		});

		//SETONACTION LAMDAS FOR CHIP COINS TO INCREMENT BET
		amount5.setOnAction(e->{
			currentBet += 5;
			betAmount.setText("$ "+ currentBet);
		});

		amount25.setOnAction(e->{
			currentBet += 25;
			betAmount.setText("$ "+ currentBet);
		});

		amount50.setOnAction(e->{
			currentBet += 50;
			betAmount.setText("$ "+ currentBet);
		});

		amount100.setOnAction(e->{
			currentBet += 100;
			betAmount.setText("$ "+ currentBet);
		});

		amount500.setOnAction(e->{
			currentBet += 500;
			betAmount.setText("$ "+ currentBet);
		});

		//RESET BET AMOUNT BACK TO ZERO
		resetCurrentBet.setOnAction(e->{
					currentBet=0;
					betAmount.setText("$ " + currentBet);
		});

		//CONFIRM BET AMOUNT AND PLAY GAME (DEAL CARDS)
    confirm.setOnAction(e->{
			//if bet choice null
				//return and give an error message ***in a new text object (add one to betting scene for error)***
			// if bet amount 0
				//return and give an error message
			//else
				DealHandsAndPlay();
    });

		//PLAY ANOTHER GAME (BACK TO BETTING SCENE)
		playAgain.setOnAction(e -> {
			pullCards();
			mainLayout.setCenter(betLayout());
			mainLayout.setRight(null);
			currentBet = 0;
			betAmount.setText("$ " + currentBet);
			betChoice.setText(" ");
			playerScore.setText("\n\n\n	Score: 0");
			bankerScore.setText("\n\n\n	Score: 0");
		});

		//MENU ITEM, START NEW FRESH GAME
		refresh.setOnAction(e -> {
			currentBet = 0;
			totalWinnings = 0;
			theDealer.generateDeck();
			theDealer.shuffleDeck();
			betAmount.setText("$ " + currentBet);
			Money.setText("$ "+ totalWinnings );
			mainLayout.setCenter(betLayout());
			mainLayout.setRight(null);
		});

		//MENU ITEM, EXIT APPLICATION
		exit.setOnAction(e -> Platform.exit());


		//CHOICE BOX LISTENER FOR CHOOSING WHO YOU ARE BETTING ON
		c.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

      	// if the item of the list is changed
		@Override
		public void changed(ObservableValue ov, Number value, Number new_value)
		{
		    // set the text for the label to the selected item
		    //l1.setText(s[new_value.intValue()] + " selected");
		    bettingOn = s[new_value.intValue()];
          	betChoice.setText(bettingOn);
		}
		});
	} //END OF START FUNCTION


	//****************************************************************************************************************
	//*****************************      LAYOUT FUNCTIONS FOR BORDERPANE    ******************************************
	//****************************************************************************************************************
	public VBox startLayout() {
		VBox startBox = new VBox(playButton);
		startBox.setPadding(new Insets(90));
		startBox.setSpacing(20);
		//TextField title = new TextField("stuff");
		startBox.setStyle("-fx-background-color:black;");
		return startBox;
	}//end createStartScene


	public VBox menuLayout(){
		VBox menuBox = new VBox();
		Menu m = new Menu("Options");
		m.getItems().addAll(refresh,exit);
    MenuBar mb = new MenuBar();
    mb.getMenus().add(m);
		menuBox.getChildren().add(mb);
		return menuBox;
	}


	public VBox betLayout() {
		HBox BetMessage = new HBox(betInstructions);
		HBox chips = new HBox(spacer,amount5,amount25,amount50,amount100, amount500);
		HBox resetB = new HBox(spacer2, resetCurrentBet);
		HBox confirmB = new HBox(spacer3, confirm);
		VBox main = new VBox(BetMessage, chips, resetB, c, confirmB);
		return main;
	}//end betLayout


	public HBox infoLayout(){
		HBox information = new HBox();
		information.setStyle("-fx-background-color: #000000");
		information.setSpacing(20);
		VBox moneyBox = new VBox(moneyInfo, Money);
		VBox betBox = new VBox(betAmountInfo, betAmount);
		VBox choice = new VBox(betChoiceInfo, betChoice);
		information.getChildren().addAll(moneyBox, betBox, choice);
		return information;
	}//end infoLayout


	public VBox gameLayout() {
		hands  = new VBox();
		player_cards = new HBox();
		banker_cards = new HBox();
		hands.setStyle("-fx-background-color: #267617;");
		player_cards.getChildren().addAll(PlayerLabel,p1,p2,p3,playerScore);
		banker_cards.getChildren().addAll(BankerLabel,b1,b2,b3,bankerScore);
		hands.getChildren().addAll(banker_cards,player_cards);
		return hands;
	}//end of gameLayout


	public VBox winnerLayout(){
		VBox winnerBox = new VBox();
		winnerBox.getChildren().add(W);
		winnerBox.setStyle("-fx-background-color: #267617;");
		return winnerBox;
	}
	//END OF LAYOUT FUNCTIONS

	//****************************************************************************************************************
	//***********************************   INITIALIZAION FUNCTIONS  *************************************************
	//****************************************************************************************************************
	public void initializeImages_and_Imageviews(){
		p1 = new ImageView(cardImages.get_background());
		p2 = new ImageView(cardImages.get_background());
		p3 = new ImageView(cardImages.get_background());
		b1 = new ImageView(cardImages.get_background());
		b2 = new ImageView(cardImages.get_background());
		b3 = new ImageView(cardImages.get_background());
		pic = new Image("file:src/test/resources/PlayButton.png");
		pic2 = new Image("file:src/test/resources/PlayButton.png", 500, 0, false, false);
		v = new ImageView(pic);
		v2 = new ImageView(pic2);
	}


	public void initializeButtons_and_setStyle(){
		confirm = new Button("Confirm");
		playButton = new Button();
		amount5 = new Button("$5");
		amount25 = new Button("$25");
		amount50 = new Button("$50");
		amount100 = new Button("$100");
		amount500 = new Button("$500");
		playAgain = new Button("Play Again");
		resetCurrentBet = new Button("Reset Bet");
		//setting styles
		playButton.setStyle("-fx-background-color:black;");
		confirm.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		amount5.setStyle("-fx-background-color: #F90808;	-fx-background-radius: 5em; -fx-min-width: 70px; -fx-min-height: 70px; -fx-max-width: 70px; -fx-max-height: 70px;");
		amount25.setStyle("-fx-background-color: #F4F407;	-fx-background-radius: 5em; -fx-min-width: 70px; -fx-min-height: 70px; -fx-max-width: 70px; -fx-max-height: 70px;");
		amount50.setStyle("-fx-background-color: #07F4B3;	-fx-background-radius: 5em; -fx-min-width: 70px; -fx-min-height: 70px; -fx-max-width: 70px; -fx-max-height: 70px;");
		amount100.setStyle("-fx-background-color: #8807F4;	-fx-background-radius: 5em; -fx-min-width: 70px; -fx-min-height: 70px; -fx-max-width: 70px; -fx-max-height: 70px;");
		amount500.setStyle("-fx-background-color: #F40793;	-fx-background-radius: 5em; -fx-min-width: 70px; -fx-min-height: 70px; -fx-max-width: 70px; -fx-max-height: 70px;");
		playAgain.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		resetCurrentBet.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		//amount5.setGraphic(amount5img);
		Glow glow = new Glow(0.5);
		confirm.setFont(redFont);
		confirm.setEffect(glow);
		playButton.setGraphic(v);
		playAgain.setFont(redFont);
		playAgain.setEffect(glow);
		resetCurrentBet.setFont(redFont);
		resetCurrentBet.setEffect(glow);
	}


	public void initializaTexts_and_setEffects(){
			W = new Text();
			spacer = new Text("		    		 				");
			spacer2 = new Text("		    		 								");
			spacer3 = new Text("		    		 								");
			spacer4 = new Text("		    		 								");
			playerScore = new Text("\n\n\n	Score: 0");
			bankerScore = new Text("\n\n\n	Score: 0");
			PlayerLabel = new Text("\n\n\n	Players Hand     \n\n\n\n\n\n\n\n");
			BankerLabel = new Text("\n\n\n	Bankers Hand     ");
			betInstructions = new Text("\n\n\n\n				Add chips to bet!");
			moneyInfo = new Text("Current Balance:");
			Money = new Text("$ " + totalWinnings);
			betAmountInfo = new Text("Current Bet Amount:");
			betAmount = new Text("$ "+ currentBet);
			betChoiceInfo = new Text("Betting on the ");
			betChoice = new Text();
			//setting font
			W.setFont(redFont);
			playerScore.setFont(redFont);
			bankerScore.setFont(redFont);
			PlayerLabel.setFont(redFont);
			BankerLabel.setFont(redFont);
			betInstructions.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
			moneyInfo.setFont(redFont);
			Money.setFont(redFont);
			betAmountInfo.setFont(redFont);
			betAmount.setFont(redFont);
			betChoiceInfo.setFont(redFont);
			betChoice.setFont(redFont);
			W.setFill(Color.ANTIQUEWHITE);
			//setting fill
			playerScore.setFill(Color.ANTIQUEWHITE);
			bankerScore.setFill(Color.ANTIQUEWHITE);
			PlayerLabel.setFill(Color.ANTIQUEWHITE);
			BankerLabel.setFill(Color.ANTIQUEWHITE);
			betInstructions.setFill(Color.ANTIQUEWHITE);
			moneyInfo.setFill(Color.ANTIQUEWHITE);
			Money.setFill(Color.ORANGERED);
			betAmountInfo.setFill(Color.ANTIQUEWHITE);
			betAmount.setFill(Color.ORANGERED);
			betChoiceInfo.setFill(Color.ANTIQUEWHITE);
			betChoice.setFill(Color.ORANGERED);
			//setting glow effect
			Glow glow = new Glow(2.0);
			W.setEffect(glow);
			playerScore.setEffect(glow);
			bankerScore.setEffect(glow);
			PlayerLabel.setEffect(glow);
			BankerLabel.setEffect(glow);
			betInstructions.setEffect(glow);
			moneyInfo.setEffect(glow);
			Money.setEffect(glow);
			betAmountInfo.setEffect(glow);
			betAmount.setEffect(glow);
			betChoiceInfo.setEffect(glow);
			betChoice.setEffect(glow);
	}


	public void initializeDataMembers(){
		mainLayout = new BorderPane();
		cardImages = new ImageArrayList();
		c = new ChoiceBox<String>(FXCollections.observableArrayList("Player", "Banker", "Tie"));
    theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		refresh = new MenuItem("Fresh Start");
    exit = new MenuItem("Exit");
		showMoneyChange_and_showWinner = 5100;
	}
	//END OF INITIALIZAION FUNCTIONS

	//****************************************************************************************************************
	//***********************************   LOGIC FUNCTIONS  *************************************************
	//****************************************************************************************************************
	public void SetScores(){
			bankerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(bankerHand));
			playerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(playerHand));
	}


	public void pullCards(){
		p1.setImage(cardImages.get_background());
		p2.setImage(cardImages.get_background());
		p3.setImage(cardImages.get_background());
		b1.setImage(cardImages.get_background());
		b2.setImage(cardImages.get_background());
		b3.setImage(cardImages.get_background());
	}


	public double evaluateWinnings() {
		winner = gameLogic.whoWon(playerHand, bankerHand);
		//check if for 8 or 9s, To check for proper win.
		int playerTotal = gameLogic.handTotal(playerHand);
		int bankerTotal = gameLogic.handTotal(bankerHand);
		boolean pNaturalWin = (playerTotal == 9 || playerTotal == 8);
		boolean bNaturalWin = (bankerTotal == 9 || bankerTotal == 8);
		if (winner == "Player" && winner == bettingOn && pNaturalWin)
			return currentBet*2;
		else if (winner == "Banker" && winner == bettingOn && bNaturalWin)
			return currentBet*2;
		else if (winner == "Draw" && winner == bettingOn && pNaturalWin && bNaturalWin)
			return currentBet*2;
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
		if (winner == bettingOn)
			return currentBet*2;
		//else it was a draw and return bids
		return 0;
	}

	public void DealHandsAndPlay(){
		mainLayout.setCenter(gameLayout());
		totalWinnings = totalWinnings - currentBet;
		Money.setText("$ "+ totalWinnings );
		betAmount.setText("$ "+ currentBet);


		if (!theDealer.EnoughtCard()){
			theDealer.generateDeck();
			theDealer.shuffleDeck();
		}
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();

		//***********************KEYVALUES****************************************************
		//current value of First parameter well be changed to Second argument
		//Dealing facedown cards (4 cards)
		KeyValue deal1 = new KeyValue(p1.imageProperty(),cardImages.get_backImage());
		KeyValue deal2 = new KeyValue(p2.imageProperty(),cardImages.get_backImage());
		KeyValue deal3 = new KeyValue(b1.imageProperty(),cardImages.get_backImage());
		KeyValue deal4 = new KeyValue(b2.imageProperty(),cardImages.get_backImage());
		KeyFrame d1  = new KeyFrame(Duration.millis(500), deal1);
		KeyFrame d2  = new KeyFrame(Duration.millis(1000), deal2);
		KeyFrame d3  = new KeyFrame(Duration.millis(1500), deal3);
		KeyFrame d4  = new KeyFrame(Duration.millis(2000), deal4);
		//Showing actual card value (4 cards)
		KeyValue show1 = new KeyValue(p1.imageProperty(),cardImages.get_suit_num(playerHand.get(0)));
		KeyValue show2 = new KeyValue(p2.imageProperty(),cardImages.get_suit_num(playerHand.get(1)));
		KeyValue show3 = new KeyValue(b1.imageProperty(),cardImages.get_suit_num(bankerHand.get(0)));
		KeyValue show4 = new KeyValue(b2.imageProperty(),cardImages.get_suit_num(bankerHand.get(1)));
		KeyFrame s1  = new KeyFrame(Duration.millis(3000), show1);
		KeyFrame s2  = new KeyFrame(Duration.millis(3700), show2);
		KeyFrame s3  = new KeyFrame(Duration.millis(4400), show3);
		KeyFrame s4  = new KeyFrame(Duration.millis(5100), show4);
		//updateing scores when ever a card is flipped (4 cards)
		KeyValue showp1Score = new KeyValue(playerScore.textProperty() ,"\n\n\n	Score: " + playerHand.get(0).getWorth());
		KeyValue showb1Score = new KeyValue(bankerScore.textProperty() ,"\n\n\n	Score: " + bankerHand.get(0).getWorth());
		KeyValue showplayerScore = new KeyValue(playerScore.textProperty() ,"\n\n\n	Score: " + gameLogic.handTotal(playerHand));
		KeyValue showbankerScore = new KeyValue(bankerScore.textProperty() ,"\n\n\n	Score: " + gameLogic.handTotal(bankerHand));
		KeyFrame PScore1 = new KeyFrame(Duration.millis(3000), showp1Score);
		KeyFrame PScore2 = new KeyFrame(Duration.millis(3700), showplayerScore);
		KeyFrame BScore1 = new KeyFrame(Duration.millis(4400), showb1Score);
		KeyFrame BScore2 = new KeyFrame(Duration.millis(5100), showbankerScore);
		//Adding everything to this timeline
		Timeline playGame = new Timeline();
		playGame.getKeyFrames().addAll(d1,d2,d3,d4,s1,s2,s3,s4,PScore1,PScore2,BScore1,BScore2);
		playGame.play();

		//at this point hands got third card is needed.
		totalWinnings += evaluateWinnings();
		finalScores.play();

		if(playerHand.size() == 3){
			showMoneyChange_and_showWinner = 7850;
			Timeline dealP3 = new Timeline();
			KeyValue deal5 = new KeyValue(p3.imageProperty(),cardImages.get_backImage());
			KeyValue show5 = new KeyValue(p3.imageProperty(),cardImages.get_suit_num(playerHand.get(2)));
			KeyFrame d5  = new KeyFrame(Duration.millis(6700), deal5);
			KeyFrame s5  = new KeyFrame(Duration.millis(7700), show5);
			dealP3.getKeyFrames().addAll(d5,s5);
			dealP3.play();
		}
		if(bankerHand.size() == 3){
			showMoneyChange_and_showWinner = 9850;
			Timeline dealB3 = new Timeline();
			KeyValue deal6 = new KeyValue(b3.imageProperty(),cardImages.get_backImage());
			KeyValue show6 = new KeyValue(b3.imageProperty(),cardImages.get_suit_num(bankerHand.get(2)));
			KeyFrame d6  = new KeyFrame(Duration.millis(8700), deal6);
			KeyFrame s6  = new KeyFrame(Duration.millis(9700), show6);
			dealB3.getKeyFrames().addAll(d6,s6);
			dealB3.play();
		}


		showPA = new Timeline(
			new KeyFrame(Duration.millis(showMoneyChange_and_showWinner),"G", showPlayAgainButton)
		);

		KeyValue showW = new KeyValue(W.textProperty(), "WINNER:				\n   "+ winner );
		KeyValue showMoney = new KeyValue(Money.textProperty(), "$ "+ totalWinnings);
		KeyFrame sW  = new KeyFrame(Duration.millis(showMoneyChange_and_showWinner), showW);
		KeyFrame sM = new KeyFrame(Duration.millis(showMoneyChange_and_showWinner), showMoney);
		Timeline ShowOutCome = new Timeline();
		ShowOutCome.getKeyFrames().addAll(sM,sW);
		ShowOutCome.play();
		showPA.play();
	}


}//END OF CLASS
