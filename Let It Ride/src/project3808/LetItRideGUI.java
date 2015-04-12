package project3808;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;

/**
 * The Graphical User Interface for Let It Ride. Initializes and sets up all components (buttons,
 * labels, etc) that make up the interface, and have a couple of helper functions that the main
 * program uses for gameplay.
 */
public class LetItRideGUI extends JFrame{
	
	JButton newRoundButton = new JButton("Start a New Round!");
	JButton rideButton = new JButton("Let It Ride");
	JButton pullButton = new JButton("Pull");
	JButton payoutsButton = new JButton("Show Payouts");
	JLabel bettingText = new JLabel("Betting Amount:");
	JLabel playerMoneyText = new JLabel("Player Money:");
	JLabel bonusBetLabel = new JLabel("Bounus Bet:");
	JCheckBox bonusCheck = new JCheckBox();
	JTextArea bettingAmount = new JTextArea(1,4);
	JTextField playerMoneyAmount = new JTextField("1000");
	JTextArea infoText = new JTextArea("Welcome to Let It Ride!");
	GridBagConstraints c = new GridBagConstraints();
	JPanel card1 = new JPanel();
	JLabel cardLabel1 = new JLabel();
	JLabel cardLabel2 = new JLabel();
	JLabel cardLabel3 = new JLabel();
	JLabel cardLabel4 = new JLabel();
	JLabel cardLabel5 = new JLabel();
	ArrayList<JLabel> cardLabel = new ArrayList<JLabel>();
	public static void main(String[] args) {
		new LetItRideGUI();
	}
	
	public LetItRideGUI() {
		this.setTitle("Let It Ride!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridBagLayout());
		
		BufferedImage myPicture = null;
		try {
		    myPicture = ImageIO.read(new File("src/images/back.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
		c.fill = GridBagConstraints.BOTH;
		cardLabel1.setDisabledIcon(new ImageIcon(myPicture));
		c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 5;
        add(cardLabel1, c);
        cardLabel1.setEnabled(false);
        cardLabel.add(cardLabel1);
        c.fill = GridBagConstraints.BOTH;
        cardLabel2.setDisabledIcon(new ImageIcon(myPicture));
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 5;
        add(cardLabel2, c);
        cardLabel2.setEnabled(false);
        cardLabel.add(cardLabel2);
        c.fill = GridBagConstraints.BOTH;
        cardLabel3.setDisabledIcon(new ImageIcon(myPicture));
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 5;
        add(cardLabel3, c);
        cardLabel3.setEnabled(false);
        cardLabel.add(cardLabel3);
        c.fill = GridBagConstraints.BOTH;
        cardLabel4.setDisabledIcon(new ImageIcon(myPicture));
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 5;
        add(cardLabel4, c);      
        cardLabel4.setEnabled(false);
        cardLabel.add(cardLabel4);
        cardLabel5.setDisabledIcon(new ImageIcon(myPicture));
        c.gridx = 9;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 5;
        add(cardLabel5, c);
        cardLabel5.setEnabled(false);
        cardLabel.add(cardLabel5);
        
		c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(bettingText, c);
        
		c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(playerMoneyText, c);
        
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(bettingAmount, c);
        bettingAmount.setText("10");
        
		c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(playerMoneyAmount, c);
        playerMoneyAmount.setEditable(false);
        
		c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(newRoundButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(rideButton, c);
        rideButton.setEnabled(false);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(pullButton, c);
        pullButton.setEnabled(false);
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(payoutsButton, c);
       
		c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 4;
        c.gridheight = 2;
        add(infoText, c);
        
        c.gridx = 9;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(bonusBetLabel, c);
        
        c.gridx = 9;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(bonusCheck, c);
        
        payoutsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					JOptionPane.showMessageDialog(null, 
							new ImageIcon(ImageIO.read(new File("src/images/PAYOUT_TABLE.png"))));
				} catch (HeadlessException | IOException e) {
					e.printStackTrace();
				}
			}
        });
        pack();
	}
	/**
	 * A function for displaying information via the GUI to the client.
	 * @param text - The text to be placed in the player's info box.
	 */
	public void setInfoText(String text){
		if (text.length() > 27){
			text = text.substring(0, 26) + "\n" + text.substring(26, text.length());
		}
		infoText.setText(text);
	}
	
	/**
	 * A function for checking if the player wishes to place a bonus bet.
	 * @return True if the bonus bet is selected, false otherwise.
	 */
	public boolean bonusIsChecked(){
		return bonusCheck.isSelected();
	}
}
