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
    cards.add(new Image("file:src/test/resources/AS.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/2S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/3S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/4S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/5S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/6S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/7S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/8S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/9S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/10S.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/JS.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/QS.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/KS.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/AH.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/2H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/3H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/4H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/5H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/6H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/7H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/8H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/9H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/10H.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/JH.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/QH.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/KH.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/AD.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/2D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/3D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/4D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/5D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/6D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/7D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/8D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/9D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/10D.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/JD.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/QD.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/KD.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/AC.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/2C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/3C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/4C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/5C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/6C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/7C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/8C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/9C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/10C.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/JC.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/QC.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/KC.jpg", 100, 175, false, false));
    cards.add(new Image("file:src/test/resources/purple_back.jpg", 100, 175, false, false));
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
