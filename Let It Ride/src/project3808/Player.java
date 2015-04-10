package project3808;
import java.util.ArrayList;

public class Player {
	private int money;
	private int maxMoney;
	private int minMoney;
	private int roundsPlayed;
	private ArrayList<Card> myHand;
	
	public Player(){
		money = 1000;
		maxMoney = 1000;
		minMoney = 1000;
		roundsPlayed = 0;
		myHand = new ArrayList<Card>();
	}
	
	public void addCard(Card c){
		myHand.add(c);
	}
	
	public ArrayList<Card> getHand(){
		return myHand;
	}
	public int getHandLength(){
		return myHand.size();
	}
	public void setHand(ArrayList<Card> hand){
		myHand = hand;
	}
	
	public int getMoney(){
		return money;
	}
	
	public int maxMoney(){
		return maxMoney;
	}
	
	public int minMoney(){
		return minMoney;
	}
	
	public int roundsPlayed(){
		return roundsPlayed;
	}
	
	public void setMoney(int i){
		money = i;
	}
	
	public void setMaxMoney(int i){
		maxMoney = i;
	}
	
	public void setMinMoney(int i){
		minMoney = i;
	}
	
	public void setRoundsPlayed(int i){
		roundsPlayed = i;
	}

	public void clearHand() {
		myHand.clear();
	}
	
}