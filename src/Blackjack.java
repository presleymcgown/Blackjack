import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import svu.csc213.Dialog;

public class Blackjack extends GraphicsProgram {

    // date about the game

    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;

    // labels to display info to the player

    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private GLabel blackjack;
    private GLabel playerDeck;
    private GLabel dealerDeck;

    // buttons for controls

    private JButton wagerButton;
    private JButton playButton;
    private JButton hitButton;
    private JButton stayButton;
    private JButton quitButton;

    // objects we are playing with

    private Deck deck;
    private GHand player;
    private GHand dealer;

    private boolean quit;


    @Override
    public void init(){


        Color background = new Color(59, 69, 135);
        this.setBackground(background);

        deck = new Deck();

        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        quitButton = new JButton("Quit");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");


        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        hitButton.setVisible(false);
        stayButton.setVisible(false);

        addActionListeners();

        blackjack = new GLabel("BLACKJACK");
        blackjack.setFont("Felix Titling-70");
        blackjack.setColor(Color.white);
        blackjack.setVisible(true);
        add(blackjack, ((getWidth() / 2) - (blackjack.getWidth() / 2)), 70);

        balanceLabel = new GLabel("YOUR BALANCE: $" + balance);
        balanceLabel.setFont("Felix Titling-24");
        balanceLabel.setColor(Color.white);
        balanceLabel.setVisible(true);
        add(balanceLabel, ((getWidth() / 2) - (balanceLabel.getWidth() / 2)), 200);

        bankLabel = new GLabel("BANK BALANCE: $" + bank);
        bankLabel.setFont("Felix Titling-24");
        bankLabel.setColor(Color.white);
        bankLabel.setVisible(true);
        add(bankLabel, ((getWidth() / 2) - (bankLabel.getWidth() / 2)), 250);

        if(bank == 0){

            quit = Dialog.getYesOrNo("You have won all the bank's money and cannot wager any more. Would you like to quit the game?");

            if(quit){
                System.exit(0);
            }

        }

        if(balance == 0){

            quit = Dialog.getYesOrNo("You have lost all your money to the bank. Would you like to quit the game?");

            if(quit){
                System.exit(0);
            }
        }


    }


    @Override
    public void actionPerformed(ActionEvent ae){

        switch ((ae.getActionCommand())){

            case "Wager":
                wager();
                break;

            case "Play":
                play();
                break;

            case "Hit":
                hit();
                break;

            case "Stay":
                stay();
                break;

            case "Quit":
                System.exit(0);
                break;

            default:
                System.out.println("I do not recognize that action command. Check your button text.");

        }

    }


    @Override
    public void run(){

        /* GHand hand = new GHand(new Hand(deck, true));
           add(hand, 100, 100);
           hand.hit(); */

    }

    private void wager(){

        if(bank == 0){

            quit = Dialog.getYesOrNo("You have won all the bank's money and cannot wager any more. Would you like to quit the game?");

            if(quit){
                System.exit(0);
            }

        }

        if(balance == 0){

            quit = Dialog.getYesOrNo("You have lost all your money to the bank. Would you like to quit the game?");

            if(quit){
                System.exit(0);
            }

        }



        if(wager <= 0){

            wager = Dialog.getInteger("How much would you like to wager?");

            if(wager <= 0){

                Dialog.showMessage("You must input a wager greater than 0. Try again.");
                wager();

            }else if(wager > balance || wager > bank){

                Dialog.showMessage("You or the bank does not have enough money for that wager. Please input a wager less than $" + balance + " or $" + bank);
                wager = 0;
                wager();

            }

            if(wager > 0 && wager <= balance){

                Dialog.showMessage("You have made a wager. You may now play the game.");

            }
            
        }



    }

    private void play(){

        if(wager == 0){
            Dialog.showMessage("You do not have a wager set. Please set a wager before you play the game.");

            wager();
        }

        wagerButton.setVisible(false);
        playButton.setVisible(false);
        hitButton.setVisible(true);
        stayButton.setVisible(true);

        blackjack.setVisible(false);

        remove(bankLabel);
        remove(balanceLabel);

        add(bankLabel,10, 30);
        add(balanceLabel, 10, 440);

        wagerLabel = new GLabel("WAGER: $" + wager);
        add(wagerLabel, 10, 235);
        wagerLabel.setFont("Felix Titling");
        wagerLabel.setColor(Color.white);

        player = new GHand(new Hand(deck, false));
        add(player, 100, 250);

        playerDeck = new GLabel("Player:");
        playerDeck.setColor(Color.white);
        playerDeck.setFont("Felix Titling-20");
        playerDeck.setVisible(true);
        add(playerDeck, 10, 320);

        dealer = new GHand((new Hand(deck, true)));
        add(dealer, 100, 75);

        dealerDeck = new GLabel("Dealer:");
        dealerDeck.setColor(Color.white);
        dealerDeck.setFont("Felix Titling-20");
        dealerDeck.setVisible(true);
        add(dealerDeck, 10, 137);

        if(player.getTotal() == 21){

            Dialog.showMessage("You have 21!");
            stay();
        }



    }

    private void hit(){

        player.hit();

        if(player.getTotal() > 21){

            Dialog.showMessage("You broke!");
            stay();

        }

    }

    private void stay() {

        hitButton.setVisible(false);
        stayButton.setVisible(false);
        quitButton.setVisible(false);

        dealer.flipCard(0);

        checkDealer();

        if(dealer.getTotal() == player.getTotal()){

            Dialog.showMessage("There was a tie, nobody won.");
            resetGame();

         }else if(dealer.getTotal() > 21 && player.getTotal() > 21){

            Dialog.showMessage("You and the dealer both broke 21, nobody won.");
            resetGame();

        }else if(dealer.getTotal() > 21){

            Dialog.showMessage("The dealer broke 21. You won!");

            balance += wager;

            bank -= wager;

            resetGame();

        }else if(player.getTotal() > 21){

            Dialog.showMessage("You broke 21. You lost!");

            balance -= wager;

            bank += wager;

            resetGame();

        }else{

            int playerRemainder;
            int bankRemainder;

            playerRemainder = 21 - player.getTotal();
            bankRemainder = 21 - dealer.getTotal();

            if(playerRemainder > bankRemainder){

                Dialog.showMessage("The dealer was closer to 21. You lost!");

                balance -= wager;

                bank += wager;

                resetGame();

            }else if(bankRemainder > playerRemainder){

                Dialog.showMessage("You are closer to 21. You won!");

                balance += wager;

                bank -= wager;

                resetGame();


            }

        }


    }

    private void checkDealer(){

        if(dealer.getTotal() < 17){

            dealer.hit();
            checkDealer();

        }

    }

    private void resetGame(){

        wager = 0;

        removeAll();
        init();

    }

    public static void main(String[] args) {

        new Blackjack().start();

    }
}
