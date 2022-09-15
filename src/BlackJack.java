import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.MouseInfo.getPointerInfo;

public class BlackJack extends  GraphicsProgram{

    // data about the game
    private int wager = 0;
    private int balance = 10000;
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


        // a gross workaround don't look at this
        GRect rect = new GRect(getWidth(), getHeight());
        rect.setColor(new Color(117, 40, 40));
        rect.setFillColor(new Color(117, 40, 40));
        rect.setFilled(true);
        add(rect, 0, 0);
        rect.addMouseListener(this);

        logo = new GImage("logo.png");
        add(logo, getWidth()/2-logo.getWidth()/2, 50);
        // The font beneath blackjack that says press any key to start
        GImage cts = new GImage("clicktostart.png");
        cts.scale(.25);
        add(cts, getWidth()/2-cts.getWidth()/2, 200);

        Runnable newThread = new Runnable(){
            public void run(){
            while(logo.getY() == 50){
                pause(500);
                cts.setVisible(false);
                pause(500);
                cts.setVisible(true);

            }
                cts.setVisible(false);
            }
        };

        this.setBackground(new Color(117, 40, 40));

        bankLabel = new GLabel("Bank: "+ bank);
        add(bankLabel, 650, 20);
        bankLabel.setVisible(false);
        bankLabel.sendToFront();

        wagerLabel = new GLabel("Wager: "+wager);
        add(wagerLabel, 650, 35);
        wagerLabel.sendToFront();
        wagerLabel.setVisible(false);

        balanceLabel = new GLabel("Balance: "+ balance);
        add(balanceLabel, 650, 50);
        bankLabel.sendToFront();
        balanceLabel.setVisible(false);

        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");

        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        hitButton.setVisible(false);
        add(stayButton, SOUTH);
        stayButton.setVisible(false);

        addActionListeners();



        this.getMenuBar().setVisible(false);
        newThread.run();
    }

    public void mouseClicked(MouseEvent e){
        setBackground(Color.white);
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
        wager = Dialog.getInteger("What's your wager");
        wagerLabel.setLabel("Wager: "+wager);

     balance -= wager;
     balanceLabel.setLabel("Balance: "+ balance);


    }

    public void play(){
        bankLabel.setVisible(true);
        balanceLabel.setVisible(true);
        wagerLabel.setVisible(true);

        if(logo.getY() != 1) {
            logo.setLocation(1, 1);
            logo.scale(.33);
        }

        deck = new Deck();
        dealerHand = new GHand(new Hand(deck, true));
        playerHand = new GHand(new Hand(deck, false));

        // setting the buttons as visible
        hitButton.setVisible(true);
        stayButton.setVisible(true);

        add(playerHand, 100, 250);
        add(dealerHand, 100, 100);





        wager();
        // making the dealers hand



    }

    private void stay(){

        dealerHand.flipCard(0);
        while(dealerHand.getTotal() < 17){
            dealerHand.hit();
        }
        if(dealerHand.getTotal() < playerHand.getTotal()){
            win();
        } else if(dealerHand.getTotal()> 21){
            lose();
        }
        if(playerHand.getTotal() < dealerHand.getTotal()){
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
        wagerLabel.setLabel("Wager: "+wager);

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
        wagerLabel.setLabel("Wager: "+wager);

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

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new BlackJack().start();

    }
}
