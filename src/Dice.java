import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Dice extends JPanel implements MouseListener {
	private int diceValue;
	private boolean diceRoll;
	private final JPanel turnPanel;
	
	/*public final int[][][] pointPositions = {
		{ {11, 11} },
		{ {4, 4}, {18, 18} },
		{ {4, 4}, {11, 11}, {18, 18} },
		{ {4, 4}, {18, 4}, {4, 18}, {18, 18} },
		{ {4, 4}, {18, 4}, {11, 11}, {4, 18}, {18, 18} },
		{ {4, 4}, {18, 4}, {4, 11}, {18, 11}, {4, 18}, {18, 18} },
	};*/
	
	public final int[][][] pointPositions = {
		{ {11, 11} },
		{ {5, 5}, {17, 17} },
		{ {5, 5}, {11, 11}, {17, 17} },
		{ {5, 5}, {17, 5}, {5, 17}, {17, 17} },
		{ {5, 5}, {17, 5}, {11, 11}, {5, 17}, {17, 17} },
		{ {5, 5}, {17, 5}, {5, 11}, {17, 11}, {5, 17}, {17, 17} },
	};
	
	public Dice(JPanel turnPanel) { 
		this.diceValue = 0;
		this.diceRoll = false;
		this.turnPanel = turnPanel;
		
		this.setupDice();
	}
	
	public Dice(int diceValue) {
		this.diceValue = diceValue;
		this.diceRoll = false;
		this.turnPanel = null;
		
		this.setupDice();
	}
	
	public void setupDice() {
		Border turnBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		this.setBorder(turnBorder);
		this.setBackground(Color.WHITE);
		
		this.setSize(26, 26);
		this.setLocation(2, 2);
		this.setOpaque(true);
		this.setVisible(true);
		this.setLayout(null);
		
		this.addMouseListener(this);
	}
	
	public void drawDice() {
		this.removeAll();
		
		int[][] pointList = this.pointPositions[this.getValue() - 1];
		
		for(int p = 0; p < pointList.length; p++) {
			JPanel dicePoint = new JPanel();
			
			dicePoint.setSize(4, 4);
			dicePoint.setLocation(pointList[p][0], pointList[p][1]);
			dicePoint.setBackground(Color.BLACK);
			dicePoint.setOpaque(true);
			dicePoint.setLayout(null);
			dicePoint.setVisible(true);
			
			this.add(dicePoint);
		}
		
		this.repaint();
	}
	
	public void rollDice() {
		Random rnd = new Random();
		this.setValue(rnd.nextInt(6) + 1);
		
		this.drawDice();
	}
	
	public void setValue(int diceValue) {
		this.diceValue = diceValue;
		this.diceRoll = true;
	}
	
	public int getValue() {
		return this.diceValue;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!this.diceRoll) {
			this.rollDice();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}
