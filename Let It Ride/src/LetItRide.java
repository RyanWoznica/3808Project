import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LetItRide extends JFrame{
	
	JButton newHandButton = new JButton("Select a New Hand");
	ArrayList<Card> deckOfCards = new ArrayList<Card>();
	ArrayList<Card> myHand = new ArrayList<Card>();
	GridBagConstraints c = new GridBagConstraints();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LetItRide("Let it Ride!");
	}
	
	LetItRide(String title) {
		
		this.setTitle(title);
		//this.setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(newHandButton, c);
        pack();
		newHandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectNewHand();
            }
        });
		for (Card.Rank rank : Card.Rank.values()){
			for (Card.Suit suit : Card.Suit.values()){
				deckOfCards.add(new Card(suit, rank));
			}
		}
	}
	
	
	
	private void selectNewHand(){
		myHand.clear();
		for (int i = 0; i < 3; i++){
			Card myCard = deckOfCards.get(((int)(Math.random()*(deckOfCards.size()-1))) + 1);
			deckOfCards.remove(myCard);
			myHand.add(myCard);
		}
		System.out.println("*****New Hand*****");
		for (Card card : myHand){
			System.out.println(card.getRank() + " of " + card.getSuit());
		}
	}
}
