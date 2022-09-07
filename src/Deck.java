import acm.util.RandomGenerator;

public class Deck {

    private Card[] cards;
    private int top;

    public Deck(){

        // initialize the array

        cards = new Card[52];

        // use enhanced for! to instantiate all the cards

        int pos = 0;

        for(Card.Suit suit : Card.Suit.values()){

            for(Card.Face face : Card.Face.values()){

                Card card = new Card(face, suit); //make a new card
                cards[pos++] = card; // put the card in the deck

            }

        }

        // call shuffle

        shuffle();

    }

    public void shuffle(){

        for (int i = 0; i < cards.length; i++) {

            // get a random number

            int randomPos = RandomGenerator.getInstance().nextInt(0, cards.length - 1);

            // store the card at the i position

            Card temp = cards[i];

            // get the card at the random number position and put it in i position

            cards[i] = cards[randomPos];

            // put the stored card back into the random number position

            cards[randomPos] = temp;


        }

        top = 0;

    }

    public Card deal(){

        if(top == cards.length){
            shuffle();
        }

        return cards[top++];

    }



}
