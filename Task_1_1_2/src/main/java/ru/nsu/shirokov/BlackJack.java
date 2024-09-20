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
        System.out.println("����� ���������� � ��������!");

        boolean gameOver = false;
        int round = 1;

        while (!gameOver) {
            System.out.println("\n����� " + round);
            playRound();
            round++;
            System.out.println("������� '0', ����� �����, ��� ����� ������ ����� ��� �����������.");
            gameOver = scanner.nextInt() == 0;
        }

        System.out.println("������� �� ����!");
    }

    private void playRound() {
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        System.out.println("���� �����: " + player.getHand() + " > " + player.getScore());
        System.out.println("����� ������: " + dealer.getHand().get(0) + ", <�������� �����>");

        if (player.hasBlackjack()) {
            System.out.println("� ��� ��������! �� ��������!");
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
            System.out.println("������� '1', ����� ����� �����, ��� '0', ����� ������������.");
            int choice = scanner.nextInt();
            if (choice == 1) {
                player.addCard(deck.drawCard());
                System.out.println("���� �����: " + player.getHand() + " > " + player.getScore());
                if (player.isBust()) {
                    System.out.println("�� ���������, ������ ������ 21 ����.");
                    break;
                }
            } else {
                break;
            }
        }
    }

    private void dealerTurn() {
        System.out.println("����� ������: " + dealer.getHand());
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.drawCard());
            System.out.println("����� ������ �����: " + dealer.getHand() + " > " + dealer.getScore());
        }

        if (dealer.isBust()) {
            System.out.println("����� ��������, ������ ������ 21 ����.");
        }
    }

    private void determineWinner() {
        if (player.isBust()) {
            System.out.println("����� �������!");
        } else if (dealer.isBust()) {
            System.out.println("�� ��������!");
        } else if (player.getScore() > dealer.getScore()) {
            System.out.println("�� �������� �����!");
        } else if (player.getScore() < dealer.getScore()) {
            System.out.println("����� ������� �����!");
        } else {
            System.out.println("�����!");
        }
    }

    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        game.playGame();
    }
}
