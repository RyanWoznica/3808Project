import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LetItRideGUI extends JFrame{
	
	JButton newRoundButton = new JButton("Start a New Round!");
	JButton rideButton = new JButton("Let It Ride");
	JButton pullButton = new JButton("Pull");
	JTextField bettingText = new JTextField("Betting Amount:");
	JTextArea bettingAmount = new JTextArea(1,4);
	GridBagConstraints c = new GridBagConstraints();

	public static void main(String[] args) {
		new LetItRideGUI();
	}
	
	public LetItRideGUI() {
		this.setTitle("Let It Ride!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(bettingText, c);
        bettingText.setEditable(false);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(bettingAmount, c);
        bettingAmount.setText("10");
		c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(newRoundButton, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(rideButton, c);
        rideButton.setEnabled(false);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(pullButton, c);
        pullButton.setEnabled(false);
        pack();
	}
}
