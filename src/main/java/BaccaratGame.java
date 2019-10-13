import javafx.scene.paint.Color;
import java.io.FileInputStream;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
//import javafx.stage.StageStyle;

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
    PauseTransition pause = new PauseTransition(Duration.seconds(3));
    HashMap<String, Scene> sceneMap;
    Image pic2 = new Image("file:src/test/resources/PlayButton.png", 500, 0, false, false );
    ImageView v2 = new ImageView(pic2);
    Label l1;
    Text Money, betAmount, betChoice, playerScore, bankerScore;
    String s[] = {"Player", "Banker", "Draw"};
    TextField value;
    Button confirm;
    Button resetCurrentBet;
    ChoiceBox<String> c;
  	ImageView p1, p2, p3, b1, b2, b3;
		Font redFont = Font.font("Times New Roman", FontWeight.BOLD, 20);



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


	//START METHOD
	@Override
	public void start(Stage primaryStage) throws Exception {
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		BorderPane mainLayout = new BorderPane();
		cardImages = new ImageArrayList();
		confirm = new Button("Confirm");
		resetCurrentBet = new Button("Reset Bet");
		value = new TextField();
		c = new ChoiceBox<String>(FXCollections.observableArrayList("Player", "Banker", "Tie"));
    theDealer = new BaccaratDealer();
		mainLayout.setCenter(startLayout());

		//PLAY BUTTON EVENT HANDLER
		playButton.setOnAction(e->{
			playButton.setGraphic(v2);
			pause.play();
			mainLayout.setCenter(betLayout());
			mainLayout.setBottom(infoLayout());
          	theDealer.generateDeck();
          	theDealer.shuffleDeck();
		});

        confirm.setOnAction(e->{
        	mainLayout.setCenter(gameLayout());
          	if (!theDealer.EnoughtCard()){
            	theDealer.generateDeck();
          		theDealer.shuffleDeck();
            }
          	playerHand = theDealer.dealHand();
          	bankerHand = theDealer.dealHand();


          /*TESTING
          System.out.println("p1 worth:"+ playerHand.get(0).getWorth());
          System.out.println("p1 value:"+ playerHand.get(0).getValue() + playerHand.get(0).getSuit());
          System.out.println("p2 worth:"+ playerHand.get(1).getWorth());
          System.out.println("p2 value:"+ playerHand.get(1).getValue() + playerHand.get(1).getSuit());

          System.out.println("\nb1 worth:"+ bankerHand.get(0).getWorth());
          System.out.println("b1 value:"+ bankerHand.get(0).getValue() + bankerHand.get(0).getSuit());
          System.out.println("b2 worth:"+ bankerHand.get(1).getWorth());
          System.out.println("b2 value:"+ bankerHand.get(1).getValue() + bankerHand.get(1).getSuit());
          *///^TESTING
          p1.setImage(cardImages.get_suit_num(playerHand.get(0)));
          b1.setImage(cardImages.get_suit_num(bankerHand.get(0)));
          b2.setImage(cardImages.get_suit_num(bankerHand.get(1)));
          p2.setImage(cardImages.get_suit_num(playerHand.get(1)));
          //SetScores();
        });



		resetCurrentBet.setOnAction(e->{
          currentBet=0;
          value.clear();
		});

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
        bankerScore.setText("Score: " + gameLogic.handTotal(bankerHand));
        playerScore.setText("Score: " + gameLogic.handTotal(playerHand));
    }


    public VBox gameLayout() {
      VBox hands  = new VBox();
      HBox player_cards = new HBox();
      HBox banker_cards = new HBox();

      Text PlayerLabel = new Text("Players Hand     ");
      Text BankerLabel = new Text("Bankers Hand     ");
	    playerScore = new Text("Score: 0");
      bankerScore = new Text("Score: 0");
      p1 = new ImageView(cardImages.get_backImage());
      p2 = new ImageView(cardImages.get_backImage());
      p3 = new ImageView(cardImages.get_backImage());
      b1 = new ImageView(cardImages.get_backImage());
      b2 = new ImageView(cardImages.get_backImage());
      b3 = new ImageView(cardImages.get_backImage());

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
		resetCurrentBet.setStyle("-fx-background-color: #DAADA3; -fx-border-style: none; -fx-text-fill: #AD1F00; -fx-padding: 8;");
		resetCurrentBet.setFont(redFont);
		confirm.setStyle("-fx-background-color: #DAADA3; -fx-border-style: none; -fx-text-fill: #AD1F00; -fx-padding: 8;");
		confirm.setFont(redFont);
		//confirm.setAlignment(CENTER); WRONG
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


}
