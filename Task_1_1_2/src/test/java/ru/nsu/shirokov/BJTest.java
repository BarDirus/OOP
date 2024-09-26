package ru.nsu.shirokov;

import java.io.ByteArrayInputStream;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BJTest {
    private final BlackJack game= new BlackJack();;
    private final Player player= new Player();;
    private final Player dealer= new Player();;
    @Test
    public void testInitialDeal() {
// Proveryayem. chto razdacha v nachale igry proiskhodit korrektno
        String simulatedInput = "0"; // Например, игрок выберет '1'
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        game.playRound();
        assertEquals(2, game.player.getHand().size(), "Igrok dolzhen poluchit 2 karty");
        assertEquals(2, game.dealer.getHand().size(), "Diler dolzhen poluchit 2 karty");
        assertTrue(game.player.getScore() > 0, "Ochki igroka dolzhny byt bolshe 0");
        assertTrue(game.dealer.getScore() > 0, "Ochki dilera dolzhny byt bolshe 0");
        game.player.reset();
        game.dealer.reset();
    }

    @Test
    public void testPlayerBlackJackWin() {
// Imitatsiya blekdzheka dlya igroka
        player.addCard(new Card("Trefy", "Tuz", 11));
        player.addCard(new Card("Chervi", "Korol", 10));

        assertTrue(player.hasBlackjack(), "Igrok dolzhen poluchit blekdzhek");
        assertFalse(dealer.hasBlackjack(), "Diler ne dolzhen poluchit blekdzhek");
        player.reset();
        dealer.reset();
    }

    @Test
    public void testDealerBlackJackWin() {
// Imitatsiya blekdzheka dlya dilera
        dealer.addCard(new Card("Chervi", "Tuz", 11));
        dealer.addCard(new Card("Bubny", "Dama", 10));

        assertTrue(dealer.hasBlackjack(), "Diler dolzhen poluchit blekdzhek");
        assertFalse(player.hasBlackjack(), "Igrok ne dolzhen poluchit blekdzhek");
        player.reset();
        dealer.reset();
    }

    @Test
    public void testPlayerBust() {
// Imitatsiya perebora u igroka
        player.addCard(new Card("Chervi", "Dama",10));
        player.addCard(new Card("Piki", "Desyatka", 10));
        player.addCard(new Card("Bubny", "Troyka", 3));

        assertTrue(player.isBust(), "Igrok dolzhen perebrat (summa > 21)");
        player.reset();
        dealer.reset();
    }

    @Test
    public void testDealerBust() {
// Imitatsiya perebora u dilera
        dealer.addCard(new Card("Piki", "Dama", 10));
        dealer.addCard(new Card("Chervi", "Desyatka", 10));
        dealer.addCard(new Card("Bubny", "Chetverka", 4));

        assertTrue(dealer.isBust(), "Diler dolzhen perebrat (summa > 21)");
        player.reset();
        dealer.reset();
    }

    @Test
    public void testPlayerWins() {
// Igrok vyigryvayet po ochkam
        game.player.addCard(new Card("Chervi", "Dama", 10));
        game.player.addCard(new Card("Bubny", "Semerka", 7));

        game.dealer.addCard(new Card("Piki", "Valet", 10));
        game.dealer.addCard(new Card("Chervi", "Shesterka", 6));

        assertEquals(game.determineWinner(),1 , "Igrok dolzhen pobedit s bolshim kolichestvom ochkov");
        game.player.reset();
        game.dealer.reset();
    }

    @Test
    public void testDealerWins() {
// Diler vyigryvayet po ochkam

        game.player.addCard(new Card("Chervi", "Shesterka", 6));
        game.player.addCard(new Card("Bubny", "Pyaterka", 5));
        game.dealer.addCard(new Card("Piki", "Dama", 10));
        game.dealer.addCard(new Card("Chervi", "Desyatka", 10));
        assertEquals(game.determineWinner(),0 , "Diler dolzhen pobedit s bolshim kolichestvom ochkov");
        game.player.reset();
        game.dealer.reset();
    }

    @Test
    public void testTie() {
// Proverka nichi
        player.addCard(new Card("Chervi", "Shesterka", 6));
        player.addCard(new Card("Bubny", "Pyaterka", 5));

        dealer.addCard(new Card("Piki", "Dama", 10));
        dealer.addCard(new Card("Chervi", "Desyatka", 10));

        assertEquals(game.determineWinner(), 100, "Igra dolzhna zakonchitsya nichyey");
        player.reset();
        dealer.reset();
    }
    @Test
    public void testDeckSize() {
        Deck deck = new Deck();
        assertEquals(52, deck.cards.size(), "Dolzhno byt 52 karty v kolode");
    }

    @Test
    public void testDrawCardReducesDeckSize() {
        Deck deck = new Deck();
        int initialSize = deck.cards.size();
        deck.drawCard();
        assertEquals(initialSize - 1, deck.cards.size(), "Posle vytyagivaniya karty razmer kolody dolzhen umenshitsya na 1");
    }

    @Test
    public void testShuffleDeck() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck1.shuffle();
        deck2.shuffle();
        assertNotEquals(deck1.cards, deck2.cards, "Peremeshannyye kolody dolzhny imet raznyye poryadki kart");
    }
    @Test
    public void testCardCreation() {
        Card card = new Card("Piki", "Desyatka", 10);
        assertEquals("Piki", card.getSuit(), "Mast karty dolzhna byt 'Piki'");
        assertEquals("Desyatka", card.getRank(), "Nominal karty dolzhen byt 'Desyatka'");
        assertEquals(10, card.getValue(), "Znacheniye karty dolzhno byt 10");
    }

    @Test
    public void testAceCardValue() {
        Card aceCard = new Card("Chervi", "Tuz", 11);
        assertEquals(11, aceCard.getValue(), "Tuz dolzhen imet znacheniye 11 po umolchaniyu");
    }
    @Test
    public void testFaceCardValues() {
        Card kingCard = new Card("Bubny", "Korol", 10);
        Card queenCard = new Card("Trefy", "Dama", 10);
        Card jackCard = new Card("Piki", "Valet", 10);

        assertEquals(10, kingCard.getValue(), "Korol dolzhen imet znacheniye 10");
        assertEquals(10, queenCard.getValue(), "Dama dolzhna imet znacheniye 10");
        assertEquals(10, jackCard.getValue(), "Valet dolzhen imet znacheniye 10");
    }

    @Test
    public void testToString() {
        Card card = new Card("Trefy", "Valet", 10);
        assertEquals("Valet Trefy (10)", card.toString(), "Metod toString() dolzhen korrektno vyvodit kartu");
    }

    @Test
    public void testCardEquality() {
        Card card1 = new Card("Piki", "Desyatka", 10);
        Card card2 = new Card("Piki", "Desyatka", 10);

        assertEquals(card1, card2, "Karty s odinakovoy mastyu. nominalom i znacheniyem dolzhny byt ravny");
    }

    @Test
    public void testCardInequality() {
        Card card1 = new Card("Piki", "Desyatka", 10);
        Card card2 = new Card("Chervi", "Desyatka", 10);

        assertNotEquals(card1, card2, "Karty s raznymi mastyami dolzhny byt ne ravny");
    }

    @Test
    public void testPlayerBlackjack() {
        Player player = new Player();
        player.addCard(new Card("Piki", "Tuz", 11));
        player.addCard(new Card("Chervi", "Desyatka", 10));

        assertTrue(player.hasBlackjack(), "Igrok dolzhen imet blekdzhek");
    }

    @Test
    public void testPlayerScoreAdjustmentForAce() {
        Player player = new Player();
        player.addCard(new Card("Piki","Tuz", 11));
        player.addCard(new Card("Chervi", "Desyatka", 10));
        player.addCard(new Card("Bubny", "Dama", 10));

        assertEquals(21, player.getScore(), "Summa ochkov dolzhna byt skorrektirovana s uchetom tuza");
    }

    @Test
    public void testPlayerScoreWithoutBust() {
        Player player = new Player();
        player.addCard(new Card("Chervi", "Devyatka", 9));
        player.addCard(new Card("Bubny", "Vosmerka", 8));

        assertFalse(player.isBust(), "Igrok ne dolzhen perebrat");
        assertEquals(17,player.getScore(), "Summa ochkov dolzhna byt 17");
    }
}