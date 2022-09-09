import acm.util.RandomGenerator;

public class Deck {

    private Card[] cards;
    private int top;

    public Deck(){
        cards = new Card[52];

        // use enhanced for loop, however it is a bad idea to use an enhanced loop when altering data in an array
        int pos = 0;
        for(Card.Suit suit : Card.Suit.values()){
            for (Card.Face face : Card.Face.values()){
                Card card = new Card(face, suit);
                cards[pos++] = card;

            }
        }
        shuffle();
    }

    public void shuffle(){
        for (int i = 0; i < cards.length; i++) {
            int rn = RandomGenerator.getInstance().nextInt(0, cards.length - 1);

            Card temp = cards[i];

            cards[i] = cards[rn];

            cards[rn] = temp;
        }
        top = 0;
    }

    public Card deal(){
        if(top == cards.length)      {
            shuffle();
        }

        return cards[top++];

    }
}
