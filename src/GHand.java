import acm.graphics.GCompound;

public class GHand extends GCompound {

    // hand data to display

    private Hand hand;

    //the GCards you will use to display them

    private GCard[] cards;

    public GHand(Hand hand){

        this.hand = hand;

        cards = new GCard[7];

        for (int i = 0; i < hand.getCount(); i++) {

            Card card = hand.getCard(i);
            GCard gcard = new GCard(card);

            // add the gcard to the compound

            add(gcard, i * (gcard.getWidth() * 1.25), 0);

            // store the GCard in the array
            
            cards[i] = gcard;



        }

    }

    // get the total value of the hand

    public int getTotal(){

        return hand.getTotal();

    }

    // flip the first card over (dealer only)

    public void flipCard(int index){

        cards[index].flip();


    }

    // draw a card from the deck (called a 'hit' in blackjack)

    public void hit(){

        hand.hit();

        // make a new GCard using the card our hand just drew

        Card temp = hand.getCard((hand.getCount() - 1));
        GCard gcard = new GCard(temp);

        // put that GCard in cards

        System.out.println("Hand count is now: " + hand.getCount());
        cards[hand.getCount() - 1] = gcard;

        // add the new gcard to the compound
        add(gcard, ((hand.getCount() -1) * (gcard.getWidth()*1.25)), 0);

    }

}
