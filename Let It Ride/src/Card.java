
public class Card {
	String value, type;
	Card(String v, String t){
		value = v;
		type = t;
		
	}
	
	String getValue(){return value;}
	String getType(){return type;}
	
	void setValue(String v){value = v;}
	void setType(String t){type = t;}
	
}
