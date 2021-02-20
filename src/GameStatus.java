import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameStatus extends JPanel {
	private final Dimension panelSize;
	private final Point panelPosition;
	
	Player[] players;
	private JPanel playerList;
	
	public GameStatus(Player[] players, Dimension panelSize, Point panelPosition) {
		this.players = players;
		this.panelSize = panelSize;
		this.panelPosition = panelPosition;
		
		this.setupPanel();
	}
	
	private void setupPanel() {
		this.setPreferredSize(new Dimension(this.panelSize.width, this.panelSize.height));
		this.setLocation(this.panelPosition.x, this.panelPosition.y);
		this.setLayout(null);
		
		Border panelBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		this.setBorder(panelBorder);
		
		this.setBackground(Color.decode("#A9A9A9"));
		this.setOpaque(true);
		this.setVisible(true);
		
		this.setupList();
	}
	
	private void setupList() {
		this.playerList = new JPanel();
		
		int listWidth = (int)(this.panelSize.width * 0.75);
		int listHeight = this.players.length * 50;
		int listOffset = (int)(this.panelSize.width * 0.125);
		
		Dimension listSize = new Dimension(listWidth, listHeight);
		Point listPosition = new Point(listOffset, listOffset);
		this.playerList.setSize(listSize);
		this.playerList.setLocation(listPosition);
		this.playerList.setLayout(null);
		
		this.playerList.setOpaque(true);
		this.playerList.setVisible(true);
		
		for(int p = 0; p < this.players.length; p++) {
			Dimension labelDimension = new Dimension(listWidth, 50);
			Point labelPosition = new Point(0, p * 50);
			
			this.players[p].setupLabel(labelDimension, labelPosition, this.playerList);
		}
		
		this.add(this.playerList);
	}
}
