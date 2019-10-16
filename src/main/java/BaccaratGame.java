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
//ANIMATION__TIMELINES__
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
//***
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
	double currentBet = 0;
	double totalWinnings = 0;
	ImageArrayList cardImages;
	Button playButton;
	String winner; //need to pass winner info accross functions e.g. evaluateWinnings()
	String bettingOn; //will either be banker, player or tie

  	//GUI STUFF
    PauseTransition pause;
    HashMap<String, Scene> sceneMap;
    Image pic2 = new Image("file:src/test/resources/PlayButton.png", 500, 0, false, false );
    ImageView v2 = new ImageView(pic2);
    Label l1;
    Text Money, betAmount, betChoice, playerScore, bankerScore, W;
    String s[] = {"Player", "Banker", "Draw"};
    TextField value;
    Button confirm, resetCurrentBet, playAgain;
    ChoiceBox<String> c;
  	ImageView p1, p2, p3, b1, b2, b3;
		Font redFont = Font.font("Times New Roman", FontWeight.BOLD, 20);
		HBox player_cards, banker_cards;
		VBox hands;
		BorderPane mainLayout;
		MenuItem refresh, exit;
		Timeline showWinner;
		int showMoneyChange_and_showWinner;



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

	public static void main(String[] args) {
		launch(args);
	}


	//START METHOD
	@Override
	public void start(Stage primaryStage) throws Exception {
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setWidth(850);
		primaryStage.setHeight(600);
		primaryStage.setResizable(false);
		pause = new PauseTransition(Duration.seconds(3));
		mainLayout = new BorderPane();
		cardImages = new ImageArrayList();
		confirm = new Button("Confirm");
		resetCurrentBet = new Button("Reset Bet");
		playAgain = new Button("Play Again");
		playAgain.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		playAgain.setFont(redFont);
		value = new TextField();
		c = new ChoiceBox<String>(FXCollections.observableArrayList("Player", "Banker", "Tie"));
    theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		refresh = new MenuItem("Fresh Start");
    exit = new MenuItem("Exit");
		mainLayout.setCenter(startLayout());
		mainLayout.setStyle("-fx-background-color: #267617;");
		showMoneyChange_and_showWinner = 5100;

		EventHandler<ActionEvent> callSetScores = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				SetScores();
			}
		};


	/*	EventHandler<ActionEvent> adjustTotalWinngs = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				Money.setText("$ "+ totalWinnings );
			}
		};
*/

		EventHandler<ActionEvent> callHandTotal = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent action) {
				playerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(playerHand));
			}
		};

		Timeline finalScores = new Timeline(
			new KeyFrame(Duration.millis(9700),"B", callSetScores),
			new KeyFrame(Duration.millis(7700),"P", callHandTotal)
		);

		//PLAY BUTTON EVENT HANDLER
		playButton.setOnAction(e->{
			playButton.setGraphic(v2);
			pause.play();
			mainLayout.setCenter(betLayout());
			mainLayout.setBottom(infoLayout());
			mainLayout.setTop(menuLayout());
    	theDealer.generateDeck();
    	theDealer.shuffleDeck();
		});

    confirm.setOnAction(e->{
    	  mainLayout.setCenter(gameLayout());
				mainLayout.setRight(winnerLayout());
				currentBet = Integer.parseInt(value.getText());
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


				KeyValue showW = new KeyValue(W.textProperty(), "		WINNER:				\n   "+ winner );
				KeyValue showMoney = new KeyValue(Money.textProperty(), "$ "+ totalWinnings);
				KeyFrame sW  = new KeyFrame(Duration.millis(showMoneyChange_and_showWinner), showW);
				KeyFrame sM = new KeyFrame(Duration.millis(showMoneyChange_and_showWinner), showMoney);
				Timeline ShowOutCome = new Timeline();
				ShowOutCome.getKeyFrames().addAll(sM,sW);
				ShowOutCome.play();


				hands.getChildren().add(playAgain);
				//layout showing winner
				//mainLayout.setRight(winnerLayout());
				//Money.setText("$ "+ totalWinnings );
    });

		playAgain.setOnAction(e -> {
			mainLayout.setCenter(betLayout());
			mainLayout.setRight(null);
			currentBet = 0;
			betAmount.setText("$ " + currentBet);
			betChoice.setText(" ");
		});


		resetCurrentBet.setOnAction(e->{
          currentBet=0;
          value.clear();
		});


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


		exit.setOnAction(e -> Platform.exit());


		//CHOICE BOX LISTENER FOR CHOOSING WHO YOU ARE BETTING ON
		c.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

      	// if the item of the list is changed
		@Override
		public void changed(ObservableValue ov, Number value, Number new_value)
		{
		    // set the text for the label to the selected item
		    l1.setText(s[new_value.intValue()] + " selected");
		    bettingOn = s[new_value.intValue()];
          	betChoice.setText(bettingOn);
		}
		});

	//using lambda for EventHandler: press enter adds info from text field to queue
      value.setOnKeyPressed(e ->{
      	if(e.getCode().equals(KeyCode.ENTER)){
      		currentBet = Integer.parseInt(value.getText());
      }});


      primaryStage.setTitle("Let's Play Baccarat!!!");
      Scene scene = new Scene(mainLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
	} //end start function


    public void SetScores(){
        bankerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(bankerHand));
        playerScore.setText("\n\n\n	Score: " + gameLogic.handTotal(playerHand));
    }


    public VBox gameLayout() {
      hands  = new VBox();
      player_cards = new HBox();
      banker_cards = new HBox();
			hands.setStyle("-fx-background-color: #267617;");
      Text PlayerLabel = new Text("\n\n\n		Players Hand     \n\n\n\n\n\n\n\n");
			PlayerLabel.setFont(redFont);
      Text BankerLabel = new Text("\n\n\n		Bankers Hand     ");
			BankerLabel.setFont(redFont);
	    playerScore = new Text("\n\n\n	Score: 0");
      bankerScore = new Text("\n\n\n	Score: 0");
			playerScore.setFont(redFont);
			bankerScore.setFont(redFont);

			p1 = new ImageView(cardImages.get_background());
			p2 = new ImageView(cardImages.get_background());
			p3 = new ImageView(cardImages.get_background());
			b1 = new ImageView(cardImages.get_background());
			b2 = new ImageView(cardImages.get_background());
			b3 = new ImageView(cardImages.get_background());

      player_cards.getChildren().addAll(PlayerLabel,p1,p2,p3,playerScore);
      banker_cards.getChildren().addAll(BankerLabel,b1,b2,b3,bankerScore);
      hands.getChildren().addAll(banker_cards,player_cards);

      return hands;
    }//end of gameLayout


	//method to create our first scene with controls
	public VBox startLayout() {
		Image pic = new Image("file:src/test/resources/PlayButton.png");
		ImageView v = new ImageView(pic);
		//v.setCache(true);
		//v.setFitHeight(146);
		//v.setFitWidth(470);
		//v.setPreserveRatio(true);
		playButton = new Button();
		//playButton.setOnAction(returnButtons);
		playButton.setGraphic(v);
		playButton.setStyle("-fx-background-color:black;");
		VBox startBox = new VBox(playButton);
		startBox.setPadding(new Insets(90));
		startBox.setSpacing(20);
		//TextField title = new TextField("stuff");
		startBox.setStyle("-fx-background-color:black;");
		return startBox;
	}//end createStartScene


	public VBox betLayout() {
		//could also do ChoiceBox : how to we allow the player to only choose one of
		//the buttons? disable buttons?
		//HBox to choose
		HBox chooseBet = new HBox();
		//this HBOX will eventually contain coins that you can choose with
		l1 = new Label("Choose who you are betting on!");
		value.setPromptText("Enter an amount to bid");
		chooseBet.getChildren().addAll(value, resetCurrentBet);
		resetCurrentBet.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		resetCurrentBet.setFont(redFont);
		confirm.setStyle("-fx-background-color: #8DE97C; -fx-border-style: none; -fx-text-fill: #148100; -fx-padding: 8;");
		confirm.setFont(redFont);

		VBox main = new VBox(chooseBet, c, confirm);
		return main;
	}//end betLayout


	public HBox infoLayout(){
		HBox information = new HBox();
		information.setStyle("-fx-background-color: #000000");
		information.setSpacing(20);

		Text moneyInfo = new Text("Current Balance:");
		moneyInfo.setFont(redFont);
		moneyInfo.setFill(Color.ANTIQUEWHITE);
		Text betAmountInfo = new Text("Current Bet Amount:");
		betAmountInfo.setFont(redFont);
		betAmountInfo.setFill(Color.ANTIQUEWHITE);
		Text betChoiceInfo = new Text("Betting on the ");
		betChoiceInfo.setFont(redFont);
		betChoiceInfo.setFill(Color.ANTIQUEWHITE);
		Money = new Text("$ " + totalWinnings);
		Money.setFont(redFont);
		Money.setFill(Color.ORANGERED);
		betAmount = new Text("$ "+ currentBet);
		betAmount.setFont(redFont);
		betAmount.setFill(Color.ORANGERED);
		betChoice = new Text();
		betChoice.setFont(redFont);
		betChoice.setFill(Color.ORANGERED);
		VBox moneyBox = new VBox(moneyInfo, Money);
		VBox betBox = new VBox(betAmountInfo, betAmount);
		VBox choice = new VBox(betChoiceInfo, betChoice);
		information.getChildren().addAll(moneyBox, betBox, choice);
		return information;
	}//end infoLayout


	public VBox winnerLayout(){
		VBox winnerBox = new VBox();
		W = new Text();
		winnerBox.getChildren().add(W);
		winnerBox.setStyle("-fx-background-color: #267617;");
		W.setFont(redFont);
		return winnerBox;
	}


	public VBox menuLayout(){
		VBox menuBox = new VBox();
		Menu m = new Menu("Options");
		m.getItems().addAll(refresh,exit);
		// create a menubar
    MenuBar mb = new MenuBar();
    // add menu to menubar
    mb.getMenus().add(m);
		menuBox.getChildren().add(mb);
		return menuBox;
	}


}
