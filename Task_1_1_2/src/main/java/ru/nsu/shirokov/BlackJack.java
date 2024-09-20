package ru.nsu.shirokov;

import java.util.Scanner;

public class BlackJack {
    private final Deck deck;
    private final Player player;
    private final Player dealer;
    private final Scanner scanner;

    public BlackJack() {
        deck = new Deck();
        deck.shuffle();
        player = new Player();
        dealer = new Player();
        scanner = new Scanner(System.in);
    }

    public void playGame() {
        System.out.println("Добро пожаловать в Блэкджэк!");

        boolean gameOver = false;
        int round = 1;

        while (!gameOver) {
            System.out.println("\nРаунд " + round);
            playRound();
            round++;
            System.out.println("Введите '0', чтобы выйти, или любое другое число для продолжения.");
            gameOver = scanner.nextInt() == 0;
        }

        System.out.println("Спасибо за игру!");
    }

    private void playRound() {
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("Ваши карты: " + player.getHand() + " > " + player.getScore());
        System.out.println("Карты дилера: " + dealer.getHand().get(0) + ", <закрытая карта>");

        if (player.hasBlackjack()) {
            System.out.println("У вас Блэкджек! Вы победили!");
            return;
        }

        playerTurn();
        if (!player.isBust()) {
            dealerTurn();
        }

        determineWinner();
    }

    private void playerTurn() {
        while (true) {
            System.out.println("Введите '1', чтобы взять карту, или '0', чтобы остановиться.");
            int choice = scanner.nextInt();
            if (choice == 1) {
                player.addCard(deck.drawCard());
                System.out.println("Ваши карты: " + player.getHand() + " > " + player.getScore());
                if (player.isBust()) {
                    System.out.println("Вы проиграли, набрав больше 21 очка.");
                    break;
                }
            } else {
                break;
            }
        }
    }

    private void dealerTurn() {
        System.out.println("Карты дилера: " + dealer.getHand());
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.drawCard());
            System.out.println("Дилер открыл карту: " + dealer.getHand() + " > " + dealer.getScore());
        }

        if (dealer.isBust()) {
            System.out.println("Дилер проиграл, набрав больше 21 очка.");
        }
    }

    private void determineWinner() {
        if (player.isBust()) {
            System.out.println("Дилер выиграл!");
        } else if (dealer.isBust()) {
            System.out.println("Вы выиграли!");
        } else if (player.getScore() > dealer.getScore()) {
            System.out.println("Вы выиграли раунд!");
        } else if (player.getScore() < dealer.getScore()) {
            System.out.println("Дилер выиграл раунд!");
        } else {
            System.out.println("Ничья!");
        }
    }

    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        game.playGame();
    }
}
