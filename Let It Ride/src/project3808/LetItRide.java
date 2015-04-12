package project3808;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import project3808.Card.Rank;

public class LetItRide extends JFrame{
	ArrayList<Card> deckOfCards = new ArrayList<Card>();
	LetItRideGUI gui;
	Player player;
	String safeRetrieval;
	int bet;
	int totalBet;
	ArrayList<String> cardNames = new ArrayList<String>();
	public LetItRide(){
		gui = new LetItRideGUI();
		player = new Player();
		
		initButtonListeners();
	}
	
	public static void main(String[] args){
		LetItRide game = new LetItRide();
	}
	
	/**
	 * A function for updating the cards displayed in the GUI.
	 */
	private void updateHand(){
		for (int i = 0; i < 5; i++){
			gui.cardLabel.get(i).setEnabled(false);
		}
		for (int i = 0; i < player.getHandLength(); i++){
			gui.cardLabel.get(i).setEnabled(true);
			if(player.getHandLength() == 0){
				gui.cardLabel.get(i).setIcon(new ImageIcon(getClass().getResource(cardNames.get(i))));
				gui.cardLabel.get(i).updateUI();
			}
			gui.cardLabel.get(i).setIcon(null);
			//gui.cardLabel.get(i).revalidate();
			gui.cardLabel.get(i).setIcon(new ImageIcon(getClass().getResource(cardNames.get(i))));
			gui.cardLabel.get(i).setHorizontalTextPosition(0);
		}
	}
	
	/**
	 * Defines functionality for the game's buttons (what they actually do when pressed).
	 */
	private void initButtonListeners() {
		gui.newRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            	//roundEnd();
            	//testCategories();
            }
        });
		gui.rideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rideBet();
            }
        });
		gui.pullButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pullBet();
            }
        });
	}
	
	/**
	 * A test function used (for testing only) for evaluating the triggering of certain events
	 * (i.e. royal flushes or straights).
	 */
	private void testCategories(){
		ArrayList<Card> testHand = new ArrayList<Card>();
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.TEN, 10));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.JACK, 11));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN, 12));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.KING, 13));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.ACE, 14));
		calculateScore(testHand, 10);
	}

	/**
	 * Performs the required procedures (retrieving a new card, raising the bet, etc) for
	 * a player to "let it [their bet] ride"
	 */
	private void rideBet() {
		totalBet += bet;
		Card card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
		deckOfCards.remove(card);
		player.addCard(card);
		cardNames.add(card.getRank() + "_" + card.getSuit() + ".png");
		updateHand();
		
		gui.setInfoText("Letting it ride! Total betting amount is now " + totalBet + "!");
		if (player.getHand().size() == 5){
			roundEnd();
		}
	}
	
	/**
	 * Performs the required procedures (retrieving a new card,  notifying the player, etc) for
	 * a player to pull their bet.
	 */
	private void pullBet(){
		Card card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
		deckOfCards.remove(card);
		player.addCard(card);
		cardNames.add(card.getRank() + "_" + card.getSuit() + ".png");
		updateHand();
		gui.setInfoText("Pulled your bet! Total betting amount is still " + totalBet + "!");
		if (player.getHand().size() == 5){
			roundEnd();
		}
	}

	/**
	 * Called when a round ends; a player's win/loss is calculated and updated in the GUI.
	 */
	private void roundEnd() {
		String result = calculateScore(player.getHand(), totalBet);
		String[] results = result.split(":");
		player.setMoney(player.getMoney() + Integer.parseInt(results[1]));
		if(gui.bonusIsChecked()){
			player.setMoney(player.getMoney() + getBonus(results[0]));
		}
		gui.setInfoText("Final Hand: " + results[0] + "!");
		gui.playerMoneyAmount.setText(Integer.toString(player.getMoney()));
		gui.pullButton.setEnabled(false);
		gui.rideButton.setEnabled(false);
		gui.newRoundButton.setEnabled(true);
	}
	
	/**
	 * Retrieves the value of a bonus bet! Called if the player has activated the bonus bet for
	 * the current round.
	 * @param string - The name of category that a hand scored.
	 * @return The value of that hand for a bonus bet (25 for a Straight, 50 for a Flush, etc)
	 */
	private int getBonus(String string) {
		if (string.equals("Royal Flush")){
			return 20000;
		}
		else if (string.equals("Straight Flush")){
			return 2000;
		}
		else if (string.equals("Four of a kind")){
			return 400;
		}
		else if (string.equals("Full House")){
			return 200;
		}
		else if (string.equals("Flush")){
			return 50;
		}
		else if (string.equals("Straight")){
			return 25;
		}
		else if (string.equals("Three of a kind")){
			return 5;
		}
		else{
			return -1;
		}
	}
	/**
	 * Calculates the player's final score, using a variety of different tests and techniques.
	 * Strategies and logic used is explained in the comments in this function.
	 * @param hand - The player's current hand to evaluate (collection of cards)
	 * @param totalBet2 - The player's current bet after all cards are revealed.
	 * @return The category of the player's hand, along with the amount of money they are awarded
	 * for it (as one string to be parsed later).
	 */
	private String calculateScore(ArrayList<Card> hand, int totalBet2) {
		HashSet<Rank> ranks = new HashSet<Rank>();
		Card.Suit targetSuit;
		int smallestValue = 20;
		int largestValue = 0;
		HashSet values = new HashSet();
		/* 
		 * Check Royal Flush - Using a set (and the properties of sets), we can collect the number
		 * of unique ranks that qualify (10 or higher - all of one suit) by simply adding them to a
		 * set. Sets won't hold duplicate values, so if all 5 unique (qualifying) ranks exist across
		 * all 5 cards, then we have a royal flush.
		 */
		targetSuit = hand.get(0).getSuit();
		for (Card card : hand){
			if (((card.getRank() == Card.Rank.TEN) || (card.getRank() == Card.Rank.JACK) || 
			(card.getRank() == Card.Rank.QUEEN) || (card.getRank() == Card.Rank.KING) ||
			(card.getRank() == Card.Rank.ACE)) && (card.getSuit() == targetSuit)){
				ranks.add(card.getRank());
				
			}
		}
		if (ranks.size() == 5){
			// Royal Flush
			return ("Royal Flush:"+(totalBet2 * 1000));
		}
		/*
		 * Check Straight Flush - Here we calculate the smallest value of the hand (rank/number of
		 * the card that's the least), along with the the greatest value of the hand for later. We
		 * also reassemble our rank set to include all cards of the suit of the first (for a straight
		 * flush, all cards will have matching suits), and create a set of values of the cards. If
		 * we have 5 unique ranks of cards with all matching hands, we can use the smallest value (x)
		 * and determine if values x,x+1,...,x+4 exist.
		 */
		ranks.clear();
		for (Card card : hand){
			if (card.getValue() < smallestValue){
				smallestValue = card.getValue();
			}
			if (card.getValue() > largestValue){
				largestValue = card.getValue();
			}
			values.add(card.getValue());
			if (card.getSuit() == targetSuit){
				ranks.add(card.getRank());
			}
		}
		if (ranks.size() == 5){
			if (values.contains(smallestValue) && values.contains(smallestValue + 1) && values.contains(smallestValue + 2) &&
					values.contains(smallestValue + 3) && values.contains(smallestValue + 4)){
				// Straight Flush
				return ("Straight Flush:" + (totalBet2 * 200));
			}
			else{
				// Flush - If we have 5 unique ranks of the same suit, we have a flush!
				return ("Flush:" + totalBet2 * 8);
			}
		}
		else{
			/*
			 * If we don't have 5 unique suits, we can still check our values and see if we have a 
			 * straight set of card values.
			 */
			if (values.contains(smallestValue) && values.contains(smallestValue + 1) && values.contains(smallestValue + 2) &&
					values.contains(smallestValue + 3) && values.contains(smallestValue + 4)){
				// Straight
				return ("Straight:" + (totalBet2 * 5));
			}
		}
		/*
		 * Here is our last major test for the remaining possible hands. We assemble a HashMap (map),
		 * which maps a key (the rank of a card) to a value (the number of cards in the
		 * and with this rank). We populate this by iterating through the hand. Then, if we have 4
		 * (and only 4) ranks, this means that the hand has two suits of the same rank, and we can
		 * check for a High Pair so long as the largest value (calculated previously) is 10 or better.
		 */
		ranks.clear();
		for(Card card : hand){
			ranks.add(card.getRank());
		}
		HashMap<Rank, Integer> map = new HashMap<Rank, Integer>();
		for(Card card : hand){
			if (map.get(card.getRank()) != null){
				int x = map.get(card.getRank());
				map.put(card.getRank(), x+1);
			}
			else{
				map.put(card.getRank(), 1);
			}
		}
		if ((ranks.size() == 4) && (largestValue >= 10)){
			for (Entry<Rank, Integer> entry : map.entrySet()){
				/*
				 * By looping through the mapping of ranks and the number of cards with each rank, we
				 * can find a high pair by checking if the rank is 10 or higher and we have 2 cards.
				 */
				if ((entry.getKey() == Rank.TEN || entry.getKey() == Rank.JACK || entry.getKey() == Rank.QUEEN || 
						entry.getKey() == Rank.KING || entry.getKey() == Rank.ACE) && (entry.getValue() >= 2)){
					return ("High Pair:" + totalBet2);
				}
				/*
				 * Similarly (but without the restriction of needing a high card), we can also check if
				 * a rank has 3 or 4 cards, which would give us 3 of a kind or 4 of a kind respectively.
				 */
				else if(entry.getValue() == 3){
					return ("Three of a kind:" + totalBet2*3);
				}
				else if(entry.getValue() == 4){
					return ("Four of a kind:" + totalBet2*50);
				}
			}
		}
		else{
			/*
			 * We can check for a Full House here. We need a rank that has 3 cards associated with it,
			 * and another rank that has 2 cards. If we go through our map and find both of these
			 * entries, we can conclude that a Full House exists!
			 */
			boolean hasThree = false;
			boolean hasTwo = false;
			for (Entry<Rank, Integer> entry : map.entrySet()){
				if (entry.getValue() == 2){
					hasTwo = true;
				}
				else if(entry.getValue() == 3){
					hasThree = true;
				}
			}
			if(hasThree && hasTwo){
				return ("Full House:" + (totalBet2 * 11));
			}
			/*
			 * We can check for a Two Pair here. Since a Two Pair will only have 3 unique ranks,
			 * we count the number of ranks that have 2 cards associated with them. If we have 2 ranks
			 * with 2 cards each, we have a pair!
			 */
			int tally = 0;
			for (Entry<Rank, Integer> entry : map.entrySet()){
				if (entry.getValue() == 2){
					tally++;
				}
			}
			if (tally == 2){
				return ("Two Pair:" + (totalBet2 * 2));
			}
			/*
			 * We also need to check for 3 of a kind or 4 of a kind again, since there can exist
			 * possible hands that also don't meet some of the criteria for a High Pair.
			 */
			for (Entry<Rank, Integer> entry : map.entrySet()){
				if(entry.getValue() == 3){
					return ("Three of a kind:" + totalBet2*3);
				}
				else if(entry.getValue() == 4){
					return ("Four of a kind:" + totalBet2*50);
				}
			}
		}
		/*
		 * And finally, we return "Less than High Pair" if we find no matches for other categories.
		 * Simple!
		 */
		return ("Less than High Pair:" + (totalBet2 * (-1)));
	}

	/**
	 * Helper function for populating the deckOfCards object with 52 new cards.
	 */
	private void initDeck(){
		int value = 2;
		deckOfCards.clear();
		for (Card.Rank rank : Card.Rank.values()){
			for (Card.Suit suit : Card.Suit.values()){
				deckOfCards.add(new Card(suit, rank, value));
			}
			value++;
		}
		
	}
	
	/**
	 * Primary function for starting a new round of Let It Ride.
	 */
	private void startNewRound(){
		initDeck();
		player.clearHand();
		updateHand();
		safeRetrieval = gui.bettingAmount.getText();
		if (!safeRetrieval.equals("")){
			try{
				bet = Integer.parseInt(safeRetrieval);
				if ((bet > player.getMoney()) || (bet < 0)){
					gui.bettingAmount.setText("0");
					bet = 0;
					gui.setInfoText("A valid betting amount must be entered!");
				}
			}
			catch (Exception e){
				gui.bettingAmount.setText("0");
				bet = 0;
				gui.setInfoText("A valid betting amount must be entered!");
			}
		}
		else{
			gui.bettingAmount.setText("0");
			bet = 0;
			gui.setInfoText("A valid betting amount must be entered!");
		}
		if (bet > 0){
			gui.setInfoText("Starting a new game!");
			totalBet = bet;
			player.setMoney(player.getMoney());
			
			Card card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
			deckOfCards.remove(card);
			player.addCard(card);
			
			card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
			deckOfCards.remove(card);
			player.addCard(card);
			
			card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
			deckOfCards.remove(card);
			player.addCard(card);
			
			cardNames.clear();
			System.out.println("*****New Hand*****");
			for (Card card1 : player.getHand()){
				System.out.println(card1.getRank() + " of " + card1.getSuit());
				cardNames.add(card1.getRank() + "_" + card1.getSuit() + ".png");
			}
			
			updateHand();
			gui.rideButton.setEnabled(true);
			gui.pullButton.setEnabled(true);
			gui.newRoundButton.setEnabled(false);
		}
	}
}