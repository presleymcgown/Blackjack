import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import java.awt.Color;
import java.util.Locale;

public class GCard extends GCompound {

    private Card card;
    private GRect back;

    public GCard(Card card){
        this.card = card;

        // make a String for the location of the card picture

        String imageFileName = "cardgifs/" + card.getSuit().toString().substring(0,1).toLowerCase() +
                                (card.getFace().ordinal() + 2) + ".gif";

        // instantiate a GImage using that String

        GImage image = new GImage(imageFileName);

        // add the GImage to the compound

        add(image, 1, 1);

        // make a GRect for the border of the card

        GRect border = new GRect(109, 152);

        // add the border to the compound

        add(border, 0, 0);

        // make a GRect for the back of the card

        back = new GRect(107, 150);
        Color backColor = new Color(180, 78, 78);
        back.setFillColor(backColor);
        back.setFilled(true);

        // add the back to the compound

        add(back, 1, 1);

        // make the visibility of the card back depend on faceUp

        back.setVisible(!card.isFaceUp());

        // scale down the card (too big)

        this.scale(.75);

    }

    public boolean getFaceUp(){
        return card.isFaceUp();
    }

    public void flip(){
        card.flip();
        back.setVisible(!back.isVisible());
    }

    public int getValue(){
        return card.getValue();
    }

}
