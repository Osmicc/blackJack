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

        deck = new Deck();
        dealerHand = new GHand(new Hand(deck, true));
        playerHand = new GHand(new Hand(deck, false));

        this.setBackground(Color.lightGray);

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
    }

    public void play(){
        // setting the buttons as visible
        hitButton.setVisible(true);
        stayButton.setVisible(true);

        wager();
        // making the dealers hand
        add(playerHand, 100, 250);
        add(dealerHand, 100, 100);
    }

    private void stay(){

    }

    private void hit(){
        playerHand.hit();
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new BlackJack().start();
    }
}
