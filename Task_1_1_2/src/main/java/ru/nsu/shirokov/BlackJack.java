package ru.nsu.shirokov;

import java.util.*;
/**
 * Реализация Блэкжэка.
 */

public class BlackJack {
    public Deck deck = new Deck();
    public Player player = new Player();
    public Player dealer = new Player();
    public Scanner scanner = new Scanner(System.in);

    /**
     * Перемешивание колод.
     */
    public BlackJack() {
        deck.shuffle();
    }
    /**
     * Начало игры.
     */

    public void playGame() {
        System.out.println("Dobro pozhalovat v Blekdzhek!");

        boolean gameOver = false;
        int round = 1;

        while (!gameOver) {
            System.out.println("\nRaund " + round);
            playRound();
            round++;
            System.out.println("Vvedite '0'. chtoby vyyti. ili lyuboye drugoye "
                    + "chislo dlya prodolzheniya.");
            gameOver = scanner.nextInt() == 0;
        }

        System.out.println("Spasibo za igru!");
    }
    /**
     * Начало раунда.
     */

    public void playRound() {
        player.reset();
        dealer.reset();
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            return;
        }
        System.out.println("Vashi karty: " + player.getHand() + " > " + player.getScore());
        System.out.println("Karty dilera: " + dealer.getHand().get(0) + ". ");

        if (player.hasBlackjack()) {
            System.out.println("U vas Blekdzhek! Vy pobedili!");
            return;
        }

        playerTurn();
        if (!player.isBust()) {
            dealerTurn();
        }

        determineWinner();
    }
    /**
     *Ход игрока.
     */

    public void playerTurn() {
        while (true) {
            System.out.println("Vvedite '1'. chtoby vzyat kartu. ili '0'. chtoby ostanovitsya.");
            int choice = scanner.nextInt();
            if (choice == 1) {
                player.addCard(deck.drawCard());
                System.out.println("Vashi karty: " + player.getHand() + " > " + player.getScore());
                if (player.isBust()) {
                    System.out.println("Vy proigrali. nabrav bolshe 21 ochka.");
                    break;
                }
            } else {
                break;
            }
        }
    }
    /**
     * Ход дилреа.
     */

    public void dealerTurn() {
        System.out.println("Karty dilera: " + dealer.getHand());
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.drawCard());
            System.out.println("Diler otkryl kartu: " + dealer.getHand()
                    + " > " + dealer.getScore());
        }

        if (dealer.isBust()) {
            System.out.println("Diler proigral. nabrav bolshe 21 ochka.");
        }
    }
    /**
     * Определение победителя.
     */

    public int determineWinner() {
        if (player.isBust() || player.getScore() < dealer.getScore()) {
            System.out.println("Diler vyigral!");
            return 0;
        } else if (dealer.isBust() || player.getScore() > dealer.getScore()) {
            System.out.println("Vy vyigrali!");
            return 1;
        } else {
            System.out.println("Nichia!");
            return 100;
        }
    }

    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        game.playGame();
    }

}
/**
 * Карты.
 */

class Card {
    private final String suit;
    private final String rank;
    private final int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ")";
    }
    // Переопределяем метод equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                Objects.equals(suit, card.suit) &&
                Objects.equals(rank, card.rank);
    }
}
/**
 * Данные о колоде.
 */

class Deck {
    public final List<Object> cards = new ArrayList<>();

    public Deck() {
        String[] suits = {"Chervi", "Bubny","Piki", "Trefy"};
        String[] ranks = {"2", "3", "4","5", "6", "7", "8", "9", "10",
                "Valet", "Dama", "Korol", "Tuz"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }
    public Card drawCard() {

        return (Card) cards.remove(cards.size()-1);
    }
}
/**
 * Данные игрока/дилера.
 */

class Player {
    private final List<Card> hand = new ArrayList<>();
    private int score = 0;
    private boolean aceAdjusted = false;

    public void addCard(Card card) {
        hand.add(card);
        score += card.getValue();
        adjustForAce();
    }
    public void reset()
    {
        hand.clear();
        score=0;
    }

    public int getScore() {
        return score;
    }

    private void adjustForAce() {
        if (score > 21 && !aceAdjusted) {
            for (Card card : hand) {
                if (card.getRank().equals("Tuz")) {
                    score -= 10;
                    aceAdjusted = true;
                    break;
                }
            }
        }
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean isBust() {
        return score > 21;
    }

    public boolean hasBlackjack() {
        return score == 21 && hand.size() == 2;
    }
}

