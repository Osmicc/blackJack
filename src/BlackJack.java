import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BlackJack extends  GraphicsProgram{

    // data about the game
    private double wager = 0;
    private float balance = 10000;
    private int bank = 10000;

    // objects we are playing with
    private Deck deck;

    public GLabel bankLabel;
    public GLabel wagerLabel;
    private GLabel balanceLabel;

    // buttons for controls
    private JButton wagerButton, playButton, hitButton, stayButton, quitButton;

    private GHand playerHand;
    private GHand dealerHand;

    @Override
    public void init() {



        this.setBackground(new Color(46, 187, 109));

        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");

        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        hitButton.setVisible(false);
        add(stayButton, SOUTH);
        stayButton.setVisible(false);
        add(quitButton, SOUTH);
        addActionListeners();

        bankLabel = new GLabel("Bank: "+ bank);
        add(bankLabel, 10, 20);

        wagerLabel = new GLabel("Wager: "+wager);
        add(wagerLabel, 100, 20);

        balanceLabel = new GLabel("Bank: "+ bank);
        add(balanceLabel, 200, 20);

        this.getMenuBar().setVisible(false);


    }

    public void actionPerformed(ActionEvent ae){
        switch (ae.getActionCommand()){
            case "Play":
                play();
                break;
            case "Stay":
                stay();
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Hit":
                hit();
                break;
            default:
                System.out.println("I do not recognize that action command");
        }
    }

    private void wager(){
        wager = Dialog.getDouble("");
        wagerLabel.setLabel("Wager: "+wager);

     balance -= wager;
     balanceLabel.setLabel("Balance: "+ balance);


    }

    public void play(){
        deck = new Deck();
        dealerHand = new GHand(new Hand(deck, true));
        playerHand = new GHand(new Hand(deck, false));

        // setting the buttons as visible
        hitButton.setVisible(true);
        stayButton.setVisible(true);

        wager();
        // making the dealers hand
        add(playerHand, 100, 250);
        add(dealerHand, 100, 100);
    }

    private void stay(){

        dealerHand.flipCard(0);
        while(dealerHand.getTotal() < 17){
            dealerHand.hit();
        }
        if(dealerHand.getTotal() < playerHand.getTotal()){
            win();
        } else if(playerHand.getTotal() < dealerHand.getTotal()){
            lose();
        } else if (playerHand.getTotal() == dealerHand.getTotal()){
            lose();
        }


    }

    private void hit(){

        if(playerHand.getTotal() <21) {
            playerHand.hit();
        }
        if(playerHand.getTotal() > 21){
            lose();
        } else if(playerHand.getTotal() == 21){
            win();
        }
    }

    public void lose(){
        Dialog.showMessage("get ratio'd loser");
        bank += wager;
        bankLabel.setLabel("Bank: "+ bank);
        wager = 0;


        if(balance <= 0){
            System.exit(0);
        }

        remove(playerHand);
        remove(dealerHand);

    }

    public void win(){
        Dialog.showMessage("your alright kid");
        balance += wager*2;
        bank -= wager;
        bankLabel.setLabel("Bank: "+ bank);
        balanceLabel.setLabel("Balance: "+ balance);

        wager =0;

        if(bank <= 0){
            Dialog.showMessage("You won!");
            System.exit(0);
        }

        // reset for next round
                
        deck.shuffle();

        playerHand.hit();

        remove(playerHand);
        remove(dealerHand);
    }

    public static void main(String[] args) {
        new BlackJack().start();
    }
}
