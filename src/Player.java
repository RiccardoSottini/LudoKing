import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Player {
	private final Color[] colors = {
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.YELLOW,
		Color.WHITE
	};
	
	private final String playerName;
	private final Cell[] playerCells;
	private final char playerCode;
	private final CellColor playerColor;
	private boolean playerWon;
	private JPanel playerLabel;
	
	private final Pawn[] pawns;
	private Pawn pawnSelected = null;
	
	private Dice dice = null;
	
	private boolean turnPlayer;
	private JPanel turnPanel;
	
	public Player(String playerName, Cell[] playerCells, char playerCode, CellColor playerColor) {
		this.playerName = playerName;
		this.playerCells = playerCells;
		this.playerCode = playerCode;
		this.playerColor = playerColor;
		this.playerWon = false;
		
		this.pawns = new Pawn[4];
		
		for(int p = 0; p < 4; p++) {
			this.pawns[p] = new Pawn(this, p, playerColor);
		}
		
		this.dice = null;
		this.turnPlayer = false;
	}
	
	int counter = 0;
	
	public void playerPlay() {
		if(!this.hasWon()) {
			this.setTurn(true);
			
			while(!this.hasDice()) {
				try {
				    Thread.sleep(100);
				} catch (Exception e) {
				    e.printStackTrace();
				}
			}
			
			System.out.println("PLAY MOVE " + this.canMove());
			
			if(this.canMove()) {
				if(this.getDice().getValue() == 6 && counter <= 3) {
					this.pawnSelected = this.pawns[counter];
				}
				counter++;
				
				while(!this.playMove()) {
					try {
					    Thread.sleep(100);
					} catch (Exception e) {
					    e.printStackTrace();
					}
				}
			}
		}
		
		this.setTurn(false);
	}
	
	public void setupLabel(Dimension labelDimension, Point labelPosition, JPanel playerList) {
		this.playerLabel = new JPanel();
		this.turnPanel = new JPanel();
		
		Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		this.playerLabel.setBorder(labelBorder);
		this.playerLabel.setBackground(colors[this.playerColor.ordinal()]);
	
		this.playerLabel.setSize(labelDimension);
		this.playerLabel.setLocation(labelPosition);
		this.playerLabel.setLayout(null);
		this.playerLabel.setVisible(true);
		
		Border turnBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		this.turnPanel.setBorder(turnBorder);
		this.turnPanel.setBackground(Color.WHITE);
		
		this.turnPanel.setSize(labelDimension.height - 20, labelDimension.height - 20);
		this.turnPanel.setLocation(10, 10);
		this.turnPanel.setLayout(null);
		this.turnPanel.setVisible(true);
		
		this.playerLabel.add(this.turnPanel);
		playerList.add(this.playerLabel);
	}
	
	public boolean isPlayerTurn() {
		return this.turnPlayer;
	}
	
	public void setTurn(boolean turnPlayer) {
		this.turnPlayer = turnPlayer;
		
		if(this.turnPlayer) {
			Border labelBorder = BorderFactory.createLineBorder(Color.ORANGE, 2);
			this.turnPanel.setBorder(labelBorder);
			
			this.setDice();
		} else {
			try {
			    Thread.sleep(1000);
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
			Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
			this.turnPanel.setBorder(labelBorder);
			
			this.deleteDice();
			this.removePawnSelected();
		}
	}
	
	public Dice getDice() {
		return this.dice;
	}
	
	public void setDice() {
		this.turnPanel.removeAll();
		
		this.dice = new Dice(this.turnPanel);
		this.turnPanel.add(this.dice);
	}
	
	public void deleteDice() {
		if(this.dice != null) {
			this.turnPanel.remove(this.dice);
			this.dice = null;
		}
	}
	
	public boolean hasDice() {
		if(this.dice != null) {
			return this.dice.getValue() != 0;
		}
		
		return false;
	}
	
	public boolean isPawnSelected() {
		return pawnSelected != null;
	}
	
	public Pawn getPawnSelected() {
		return pawnSelected;
	}
	
	public void setPawnSelected(Pawn pawnSelected) {
		this.pawnSelected = pawnSelected;
	}
	
	public void removePawnSelected() {
		this.pawnSelected = null;
	}
	
	public boolean canMove() {
		for(Pawn pawn : this.pawns) {
			if(pawn.getStatus()) {
				return true;
			}
		}
		
		if(this.hasDice()) {
			return this.getDice().getValue() == 6;
		}
		
		return false;
	}
	
	public boolean playMove() {
		if(this.isPlayerTurn()) {
			if(this.isPawnSelected()) {
				if(this.hasDice()) {
					Pawn selectedPawn = this.getPawnSelected();
					int changePosition = this.getDice().getValue();
					
					selectedPawn.movePawn(changePosition);
					
					return true;
				}
			}
		}
		
		return false;
		
		/*System.out.println("Player: " + getCode());
		System.out.println("Dice: " + this.dice.getValue());
		
		for(int c = 0; c < 57; c++) {
			System.out.println("Cell #" + c + ", Color: " + playerCells[c].getColor() + ", Type: " + playerCells[c].getType());
			System.out.println(playerCells[c].getPawns().size());
		}*/
	}
	
	public Cell getCell(int position) {
		if(position >= 0) {
			return this.playerCells[position];
		}
		
		return null;
	}
	
	public char getCode() {
		return this.playerCode;
	}
	
	public void checkWon() {
		int counter = 0;
		
		for(Pawn pawn : this.pawns) {
			if(pawn.hasWon()) {
				counter++;
			}
		}
		
		this.playerWon = counter == 4;
		
		if(this.playerWon) {
			System.out.println("Player " + this.getCode() + " has won!");
		}
	}
	
	public boolean hasWon() {
		return this.playerWon;
	}
}
