import acm.graphics.GImage;
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
    private JButton wagerButton, playButton, hitButton, stayButton;

    private GHand playerHand;
    private GHand dealerHand;

    public GImage logo;
    public GImage cts;

    @Override
    public void init() {

        logo = new GImage("logo.png");
        add(logo, getWidth()/2-logo.getWidth()/2, 50);
        // The font beneath blackjack that says press any key to start
        GImage cts = new GImage("clicktostart.png");
        cts.scale(.25);
        add(cts, getWidth()/2-cts.getWidth()/2, 200);

        Runnable newThread = new Runnable(){
            public void run(){
            while(logo.getY() == 50){
                cts.setVisible(false);
                pause(500);
                cts.setVisible(true);
                pause(500);
            }
                cts.setVisible(false);
            }
        };

        this.setBackground(new Color(117, 40, 40));

        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");

        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        hitButton.setVisible(false);
        add(stayButton, SOUTH);
        stayButton.setVisible(false);
        addActionListeners();

        bankLabel = new GLabel("Bank: "+ bank);
        bankLabel.setVisible(false);
        add(bankLabel, 10, 20);

        wagerLabel = new GLabel("Wager: "+wager);
        wagerLabel.setVisible(false);
        add(wagerLabel, 100, 20);

        balanceLabel = new GLabel("Bank: "+ bank);
        balanceLabel.setVisible(false);
        add(balanceLabel, 200, 20);

        this.getMenuBar().setVisible(false);
        newThread.run();
    }

    public void actionPerformed(ActionEvent ae){
        switch (ae.getActionCommand()){
            case "Play":
                play();
                break;
            case "Stay":
                stay();
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
        if(logo.getY() != 1) {
            logo.setLocation(1, 1);
            logo.scale(.5);
        }

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
        remove(logo);

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
