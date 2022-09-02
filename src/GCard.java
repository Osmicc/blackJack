import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import java.awt.Color;
import java.util.Locale;

public class GCard extends GCompound{

    private Card card;
    private GRect back;


    public void flip(){
        card.flip();
    }

    public boolean getFaceUp(){
        return card.isFaceUp();
    }

    public int getValue(){
        return card.getValue();
    }

    public GCard(Card card){
        this.card = card;

        String imageFileName = "cardgifs/cardgifs/" + card.getSuit().toString().substring(0, 1).toLowerCase() + (card.getFace().ordinal() + 2) + ".gif";

        GImage image = new GImage(imageFileName);

        add(image, 1, 1);

        GRect border = new GRect(109, 152);

        add(border);

        GRect back = new GRect(107, 150);

        back.setFillColor(Color.black);
        back.setFilled(true);

        add(back, 1, 1);

        back.setVisible(!card.isFaceUp());

        this.scale(.75);
    }
}
