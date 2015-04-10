package project3808;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
	private void updateHand(){
		for (int i = 0; i < 5; i++){
			gui.cardLabel.get(i).setEnabled(false);
		}
		BufferedImage myPicture = null;
		for (int i = 0; i < player.getHandLength(); i++){
			gui.cardLabel.get(i).setEnabled(true);
			if(player.getHandLength() == 0){
				try {
				    myPicture = ImageIO.read(new File("src/images/back.png"));
				} catch (IOException e) {
					System.out.println(e);
				}	
				
				gui.cardLabel.get(i).setIcon(new ImageIcon(myPicture));
				gui.cardLabel.get(i).updateUI();
			}
			
			try {
			    myPicture = ImageIO.read(new File("src/images/" + cardNames.get(i)));
			} catch (IOException e) {
				System.out.println(e);
			}
			gui.cardLabel.get(i).setIcon(null);
			//gui.cardLabel.get(i).revalidate();
			gui.cardLabel.get(i).setIcon(new ImageIcon(myPicture));
			gui.cardLabel.get(i).setHorizontalTextPosition(0);
		}
	}
	private void initButtonListeners() {
		gui.newRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
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
	
	private void testCategories(){
		ArrayList<Card> testHand = new ArrayList<Card>();
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.TEN, 10));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.JACK, 11));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN, 12));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.KING, 13));
		testHand.add(new Card(Card.Suit.SPADES, Card.Rank.ACE, 14));
		calculateScore(testHand, 10);
	}

	private void rideBet() {
		totalBet += bet;
		Card card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
		deckOfCards.remove(card);
		player.addCard(card);
		cardNames.add(card.getRank() + "_" + card.getSuit() + ".png");
		updateHand();
		
		System.out.println("Letting it ride! Total betting amount is now " + totalBet + " with the following cards:");
		for (Card playerCard : player.getHand()){
			System.out.println(playerCard.getRank() + " of " + playerCard.getSuit());
		}
		if (player.getHand().size() == 5){
			roundEnd();
		}
	}
	
	private void pullBet(){
		Card card = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
		deckOfCards.remove(card);
		player.addCard(card);
		cardNames.add(card.getRank() + "_" + card.getSuit() + ".png");
		updateHand();
		System.out.println("Pulled your bet! Total betting amount is still " + totalBet + " with the following cards:");
		for (Card playerCard : player.getHand()){
			System.out.println(playerCard.getRank() + " of " + playerCard.getSuit());
		}
		if (player.getHand().size() == 5){
			roundEnd();
		}
	}

	private void roundEnd() {
		String result = calculateScore(player.getHand(), totalBet);
		String[] results = result.split(":");
		player.setMoney(player.getMoney() + Integer.parseInt(results[1]));
		System.out.println("Final Hand: " + results[0] + "!");
		gui.playerMoneyAmount.setText(Integer.toString(player.getMoney()));
		gui.pullButton.setEnabled(false);
		gui.rideButton.setEnabled(false);
		gui.newRoundButton.setEnabled(true);
	}

	// Calculates the player's final score. Note that we use a nice attribute of sets, which only
	// keeps unique values. This makes it easy to find the number of distinct ranks in a hand!
	private String calculateScore(ArrayList<Card> hand, int totalBet2) {
		HashSet<Card.Rank> ranks = new HashSet<Card.Rank>();
		
		Card.Suit targetSuit;
		int smallestValue = 20;
		int largestValue = 0;
		HashSet values = new HashSet();
		//Check Royal Flush
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
		// Check Straight Flush
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
				// Straight Flush [x]
				return ("Straight Flush:" + (totalBet2 * 200));
			}
			else{
				// Flush [x]
				return ("Flush:" + totalBet2 * 8);
			}
		}
		else{
			// Check for straight
			if (values.contains(smallestValue) && values.contains(smallestValue + 1) && values.contains(smallestValue + 2) &&
					values.contains(smallestValue + 3) && values.contains(smallestValue + 4)){
				// Straight [x]
				return ("Straight:" + (totalBet2 * 5));
			}
		}
		ranks.clear();
		for (Card card : hand){
			ranks.add(card.getRank());
		}
		// Check for three-of-a-kind, four-of-a-kind, full house
		if (ranks.size() == 3){
			int tally = 0;
			for(Card card : hand){
				if (card.getValue() == largestValue){
					tally += 1;
				}
			}
			if ((tally == 2) || (tally == 3)){
				// Three-of-a-kind [x]
				return ("Three of a kind:" + (totalBet2 * 3));
			}
		}
		else if (ranks.size() == 2){
			// 4 OAK or Full House
			int tally = 0;
			for(Card card : hand){
				if (card.getValue() == largestValue){
					tally += 1;
				}
			}
			if ((tally == 2) || (tally == 3)){
				// Full House [x]
				return ("Full House:" + (totalBet2 * 11));
			}
			else{
				// 4 OAK [x]
				return ("Four of a kind:" + totalBet2 * 50);
			}
			
		}
		else if ((ranks.size() == 4) && (largestValue >= 10)){
			int tally = 0;
			for(Card card : hand){
				//if (card.getValue() == largestValue){
				//	tally += 1;
				//}
				for (int i = 0; i < hand.size(); i++){
			
					if(card.getValue() == hand.get(i).getValue() && card.getValue() >= 10){
						if(card.getSuit() != hand.get(i).getSuit()){
							tally += 1;
						}
					}	
				}
			}			
			if (tally == 2){
				// High Pair [x]
				return ("High Pair:" + totalBet2);
			}
		}
		// No matches found, return a loss [x]
		return ("Less than High Pair:" + (totalBet2 * (-1)));
	}

	private void initDeck(){
		int value = 2;
		for (Card.Rank rank : Card.Rank.values()){
			for (Card.Suit suit : Card.Suit.values()){
				deckOfCards.add(new Card(suit, rank, value));
			}
			value++;
		}
		
	}
	
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
					System.out.println("A valid betting amount must be entered!");
				}
			}
			catch (Exception e){
				gui.bettingAmount.setText("0");
				bet = 0;
				System.out.println("A valid betting amount must be entered!");
			}
		}
		else{
			gui.bettingAmount.setText("0");
			bet = 0;
			System.out.println("A valid betting amount must be entered!");
		}
		if (bet > 0){
			
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
	
	public static void main(String[] args){
		LetItRide game = new LetItRide();
	}
}