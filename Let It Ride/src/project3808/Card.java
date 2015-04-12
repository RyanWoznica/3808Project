package project3808;

/**
 * A simple class for representing a single card in-game.
 */
public class Card {
	public enum Suit {CLUBS, SPADES, HEARTS, DIAMONDS};
	public enum Rank {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}
	Suit suit;
	Rank rank;
	int value;
	
	Card(Suit s, Rank r, int v){
		suit = s;
		rank = r;
		value = v;
	}

	Suit getSuit(){return suit;}
	Rank getRank(){return rank;}
	int getValue(){return value;}
	
	void setSuit(Suit s){suit = s;}
	void setType(Rank r){rank = r;}
	void setValue(int i){value = i;}
}
