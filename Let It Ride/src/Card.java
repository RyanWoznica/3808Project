
public class Card {
	public enum Suit {CLUBS, SPADES, HEARTS, DIAMONDS};
	public enum Rank {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}
	Suit suit;
	Rank rank;
	Card(Suit s, Rank r){
		suit = s;
		rank = r;
	}
	
	Suit getSuit(){return suit;}
	Rank getRank(){return rank;}
	
	void setSuit(Suit s){suit = s;}
	void setType(Rank r){rank = r;}
}
