import acm.graphics.GCompound;

public class GHand extends GCompound {
    private Hand hand;

    private GCard[] gCards;

    public GHand(Hand hand){
        this.hand = hand;
        gCards = new GCard[7];

        for (int i = 0; i < hand.getCount(); i++) {
            Card card = hand.getCard(i);
            GCard gcard = new GCard(card);

            add(gcard, i * gcard.getWidth() + gcard.getWidth()/4, getHeight()/2);
            gCards[i] = gcard;

        }
    }

    public int getTotal(){
        return hand.getTotal();
    }

    public void flipCard(int index){
        gCards[index].flip();
    }

    public void hit(){
        hand.hit();

        GCard gCard = new GCard(hand.getCard(hand.getCount()-1));

        gCards[hand.getCount()-1] = gCard;

        add(gCard, 0, 0);
    }




}
