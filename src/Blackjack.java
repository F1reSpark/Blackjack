import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {

    private Deck deck;
    private ArrayList<Card> player;
    private ArrayList<Card> dealer;
    private Scanner kb;
    private Boolean cont = true;
    private Boolean gambleBig = true;
    private Boolean betStage = true;
    private Boolean startGame = true;
    private int dealerValue = 0;
    private int playerValue = 0;
    private static int playerMoney = 300;
    private int minBet = 50;
    private int betAmt;
    private int betPot;
    private Card card;

    //private String response = kb.nextLine();

    public Blackjack(){
        deck = new Deck();
        player = new ArrayList<>();
        dealer = new ArrayList<>();
        kb = new Scanner(System.in);
    }
    public static void main(String[] args) {
        Blackjack game =  new Blackjack();
        game.run();
    }
    private void run(){
        while (betStage) {
            bet();
        }
        System.out.println("Current pot: $" + betPot);
        System.out.println();

        deck.shuffle();
        playerGetCard();
        dealerGetCard();
        playerGetCard();
        dealerGetCard();
        calcPlayerCards();
        calcDealerCards();

        if(playerValue > 21){
            System.out.println("You went over 21 - You lose!");
            System.out.println();
            System.out.println("Current Money: $" + playerMoney);
            if (playerMoney == 0){
                System.out.println("No money remaining!");
                System.exit(0);
            }
            cont = false;
        } else if(dealerValue > 21){
            System.out.println("Dealer went over 21 - You win!");
            playerMoney += betPot;
            System.out.println();
            System.out.println("Current Money: $" + playerMoney);
            cont = false;
        } else if (playerValue == 21) {
            System.out.println("You met 21 - You win!");
            playerMoney += betPot;
            System.out.println();
            System.out.println("Current Money: $" + playerMoney);
            cont = false;
        } else if (dealerValue == 21) {
            System.out.println("Dealer met 21 - You lose!");
            System.out.println();
            System.out.println("Current Money: $" + playerMoney);
            if (playerMoney == 0){
                System.out.println("No money remaining!");
                System.exit(0);
            }
            cont = false;
        }else {
            while (cont) {
                System.out.print("Player hand: ");
                for(int i = 0; i < player.size(); i++){
                    System.out.print(player.get(i) + " ");
                }
                System.out.println();
                System.out.println("Value: " + playerValue);
                System.out.println();


                System.out.print("Dealer hand: ");
                System.out.print("??? ");
                for(int i = 1; i < dealer.size(); i++){
                    System.out.print(dealer.get(i) + " ");
                }
                System.out.println();
                System.out.println();

                if(playerValue > 21){
                    System.out.println("You went over 21 - You lose!");
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                    if (playerMoney == 0){
                        System.out.println("No money remaining!");
                        System.exit(0);
                    }
                    cont = false;
                } else if(dealerValue > 21) {
                    System.out.println("Dealer went over 21 - You win!");
                    playerMoney += betPot;
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                    cont = false;
                } else if (playerValue == 21) {
                    System.out.println("You met 21 - You win!");
                    playerMoney += betPot;
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                    cont = false;
                } else if (dealerValue == 21) {
                    System.out.println("Dealer met 21 - You lose!");
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                    if (playerMoney == 0){
                        System.out.println("No money remaining!");
                        System.exit(0);
                    }
                    cont = false;
                }

                doubleDown();
                if(cont) {
                    hitOrStay();
                }
            }
        }
        if (playerMoney == 0){
            System.out.println("No money remaining!");
            System.exit(0);
        }
        replay();
    }

    private void dealerGetCard(){
        dealer.add(deck.getCard());
    }
    private void playerGetCard(){
        player.add(deck.getCard());
    }

    private void bet(){
        if(playerMoney < minBet){
            minBet = playerMoney;
        }
        System.out.println("Current Money: $" + playerMoney);
        System.out.println();
        System.out.println("Bet a minimum of $" + minBet + " [Type a whole number]");
        if (!kb.hasNextInt()) {
            kb.nextLine();
            System.out.println("Invalid Amount!");
            bet();
        } else {
            betAmt = kb.nextInt();
            kb.nextLine();
            if (betAmt >= minBet && betAmt <= playerMoney) {
                playerMoney -= betAmt;
                betPot += 2*betAmt;
                betStage = false;
            } else {
                System.out.println("Invalid Amount!");
                bet();
            }
        }
    }
    private void calcDealerCards(){
        dealerValue = 0;
        for(int i = 0; i < dealer.size(); i++){
            dealerValue += dealer.get(i).getValue();
        }
        for(int i = 0; i < dealer.size(); i++){
            if(dealerValue > 21  && dealer.get(i).getValue() == 11){
                dealerValue -= 10;
            }
        }
    }
    private void calcPlayerCards(){
        playerValue = 0;
        for(int i = 0; i < player.size(); i++){
            playerValue += player.get(i).getValue();
        }
        for(int i = 0; i < player.size(); i++){
            if(playerValue > 21 && player.get(i).getValue() == 11){
                playerValue -= 10;
            }
        }
    }
    private void doubleDown(){
        System.out.println("Double down? [Y/N]");
        if(gambleBig) {
            switch (kb.nextLine()) {
                case "Y":
                    betPot *= 2;
                    System.out.println("Current pot: $" + betPot);
                    System.out.println();

                    playerGetCard();
                    calcPlayerCards();
                    calcDealerCards();

                    System.out.print("Player hand: ");
                    for(int i = 0; i < player.size(); i++){
                        System.out.print(player.get(i) + " ");
                    }
                    System.out.println();
                    System.out.println("Value: " + playerValue);
                    System.out.println();


                    System.out.print("Dealer hand: ");
                    System.out.print("??? ");
                    for(int i = 1; i < dealer.size(); i++){
                        System.out.print(dealer.get(i) + " ");
                    }
                    System.out.println();
                    System.out.println();

                    if (playerValue == dealerValue) {
                        System.out.println("Push!");
                        playerMoney += betPot/2;
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                    } else if (playerValue > 21) {
                        System.out.println("You went over 21 - You lose!");
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                        if (playerMoney == 0){
                            System.out.println("No money remaining!");
                            System.exit(0);
                        }
                    } else if (dealerValue > 21) {
                        System.out.println("Dealer went over 21 - You win!");
                        playerMoney += betPot;
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                    }else if (playerValue == 21) {
                        System.out.println("You reached 21 - You win!");
                        playerMoney += betPot;
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                    } else if (dealerValue == 21) {
                        System.out.println("Dealer reached 21 - You lose!");
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                        if (playerMoney == 0){
                            System.out.println("No money remaining!");
                            System.exit(0);
                        }
                    } else if (dealerValue > playerValue) {
                        System.out.println("You scored lower than the dealer - You lose!");
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                        if (playerMoney == 0){
                            System.out.println("No money remaining!");
                            System.exit(0);
                        }
                    } else if (playerValue > dealerValue) {
                        System.out.println("You scored higher than the dealer - You win!");
                        playerMoney += betPot;
                        System.out.println();
                        System.out.println("Current Money: $" + playerMoney);
                    } else {
                        System.out.println("This shouldn't appear unless something went wrong");
                    }
                    if (playerMoney == 0){
                        System.exit(0);
                    }
                    gambleBig = false;
                    cont = false;
                    break;
                case "N":
                    gambleBig = false;
                    cont = true;
                    break;
                default:
                    System.out.println("Invalid Response!");
                    doubleDown();
            }
        }
    }
    private void hitOrStay() {
        System.out.println("Hit or Stay? [H/S]");
        switch (kb.nextLine()) {
            case "H":
                playerGetCard();
                if(dealerValue < 17){
                    dealerGetCard();
                }
                calcPlayerCards();
                calcDealerCards();
                cont = true;
                break;
            case "S":
                if (playerValue == dealerValue){
                    System.out.println("Push!");
                    playerMoney += betPot/2;
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                } else if (playerValue == 21){
                    System.out.println("You reached 21 - You win!");
                    playerMoney += betPot;
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                } else if (dealerValue == 21){
                    System.out.println("Dealer reached 21 - You lose!");
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                } else if (dealerValue > playerValue){
                    System.out.println("You scored lower than the dealer - You lose!");
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                } else if (playerValue > dealerValue){
                    System.out.println("You scored higher than the dealer - You win!");
                    playerMoney += betPot;
                    System.out.println();
                    System.out.println("Current Money: $" + playerMoney);
                } else {
                    System.out.println("This shouldn't appear unless something went wrong");
                }
                cont = false;
                break;
            default:
                System.out.println("Invalid Response!");
                hitOrStay();
        }
    }
    private void replay(){
        System.out.println();
        System.out.println("Play again? [Y/N]");
        switch (kb.nextLine()){
            case "Y":
                Blackjack newgame = new Blackjack();
                newgame.run();
                break;

            case "N":
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Invalid Response!");
                replay();
        }
    }
}