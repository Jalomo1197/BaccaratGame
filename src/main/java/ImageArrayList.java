import java.util.ArrayList;
import javafx.scene.image.Image;

//SPADES = 0
//HEARTS = 1
//DIAMONDS =2
//CLUBS = 3

public class ImageArrayList{
  ArrayList<Image> cards;

  ImageArrayList(){
    cards = new ArrayList<Image>();
    //SPADES, HEARTS, DIAMONDS, CLUBS
    cards.add(new Image("file:src/test/resources/AS.jpg"));
    cards.add(new Image("file:src/test/resources/2S.jpg"));
    cards.add(new Image("file:src/test/resources/3S.jpg"));
    cards.add(new Image("file:src/test/resources/4S.jpg"));
    cards.add(new Image("file:src/test/resources/5S.jpg"));
    cards.add(new Image("file:src/test/resources/6S.jpg"));
    cards.add(new Image("file:src/test/resources/7S.jpg"));
    cards.add(new Image("file:src/test/resources/8S.jpg"));
    cards.add(new Image("file:src/test/resources/9S.jpg"));
    cards.add(new Image("file:src/test/resources/10S.jpg"));
    cards.add(new Image("file:src/test/resources/JS.jpg"));
    cards.add(new Image("file:src/test/resources/QS.jpg"));
    cards.add(new Image("file:src/test/resources/KS.jpg"));
    cards.add(new Image("file:src/test/resources/AH.jpg"));
    cards.add(new Image("file:src/test/resources/2H.jpg"));
    cards.add(new Image("file:src/test/resources/3H.jpg"));
    cards.add(new Image("file:src/test/resources/4H.jpg"));
    cards.add(new Image("file:src/test/resources/5H.jpg"));
    cards.add(new Image("file:src/test/resources/6H.jpg"));
    cards.add(new Image("file:src/test/resources/7H.jpg"));
    cards.add(new Image("file:src/test/resources/8H.jpg"));
    cards.add(new Image("file:src/test/resources/9H.jpg"));
    cards.add(new Image("file:src/test/resources/10H.jpg"));
    cards.add(new Image("file:src/test/resources/JH.jpg"));
    cards.add(new Image("file:src/test/resources/QH.jpg"));
    cards.add(new Image("file:src/test/resources/KH.jpg"));
    cards.add(new Image("file:src/test/resources/AD.jpg"));
    cards.add(new Image("file:src/test/resources/2D.jpg"));
    cards.add(new Image("file:src/test/resources/3D.jpg"));
    cards.add(new Image("file:src/test/resources/4D.jpg"));
    cards.add(new Image("file:src/test/resources/5D.jpg"));
    cards.add(new Image("file:src/test/resources/6D.jpg"));
    cards.add(new Image("file:src/test/resources/7D.jpg"));
    cards.add(new Image("file:src/test/resources/8D.jpg"));
    cards.add(new Image("file:src/test/resources/9D.jpg"));
    cards.add(new Image("file:src/test/resources/10D.jpg"));
    cards.add(new Image("file:src/test/resources/JD.jpg"));
    cards.add(new Image("file:src/test/resources/QD.jpg"));
    cards.add(new Image("file:src/test/resources/KD.jpg"));
    cards.add(new Image("file:src/test/resources/AC.jpg"));
    cards.add(new Image("file:src/test/resources/2C.jpg"));
    cards.add(new Image("file:src/test/resources/3C.jpg"));
    cards.add(new Image("file:src/test/resources/4C.jpg"));
    cards.add(new Image("file:src/test/resources/5C.jpg"));
    cards.add(new Image("file:src/test/resources/6C.jpg"));
    cards.add(new Image("file:src/test/resources/7C.jpg"));
    cards.add(new Image("file:src/test/resources/8C.jpg"));
    cards.add(new Image("file:src/test/resources/9C.jpg"));
    cards.add(new Image("file:src/test/resources/10C.jpg"));
    cards.add(new Image("file:src/test/resources/JC.jpg"));
    cards.add(new Image("file:src/test/resources/QC.jpg"));
    cards.add(new Image("file:src/test/resources/KC.jpg"));
    cards.add(new Image("file:src/test/resources/purple_back.jpg"));
  }

  public Image get_suit_num(Card card){
    String suit = card.getSuit();
    int val = card.getValue() - 1;
    int offSet = 0;
    switch(suit){
      case "Spades":
        offSet = 0;
      case "Hearts":
        offSet = 14;
      case "Diamonds":
        offSet = 27;
      case "Clubs":
        offSet = 40;
    }

    return cards.get(offSet + val);
  }


  public Image get_backImage(){
    return cards.get(52);
  }


}
