import javax.swing.JFrame;
import javax.swing.JLabel;



public class LetItRide extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LetItRide("Let it Ride!");
	}
	
	LetItRide(String title) {
		
		this.setTitle(title);
		this.setSize(1000, 1000);
		// pack();
		setVisible(true);
	}
}
