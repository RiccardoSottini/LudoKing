import java.util.ArrayList;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

enum CellColor {
	Blue,
	Red, 
	Green,
	Yellow,
	White
};

enum CellType {
	Open,
	Star,
	Close,
	End
};

public class Launcher extends JFrame {
	private final char[] playerCodes = {'B', 'R', 'G', 'Y'};
	
	private final int MAX_PLAYERS = 4;
	private final int OPEN_CELLS = 52;

	private Cell[] openCells;
	private Cell[][] closeCells;
	
	private int nPlayers;
	private Player[] players;
	private String[] playerNames;
	
	/* CHANGE */
	private GameMenu gameMenu;
	private GameBoard gameBoard;
	private GameStatus gameStatus;
	
	/* CHANGE */
	public Launcher() {	
		this.setupFrame();
		
		this.runMenu();
		this.runGame();
	}
	
	public boolean isFinished() {
		int playerCounter = 0;
		
		for(Player player : this.players) {
			if(player.hasWon()) {
				playerCounter++;
			}
		}
		
		return playerCounter == (nPlayers - 1);
	}
	
	/* CHANGE */
	public void runMenu() {
		this.gameMenu = new GameMenu(new Dimension(600, 600));
		this.add(this.gameMenu);
		
		this.pack();
		this.setVisible(true);
		
		this.gameMenu.waitMenu();
		this.playerNames = this.gameMenu.getPlayers();
		this.nPlayers = this.playerNames.length;
		
		this.setVisible(false);
		this.remove(this.gameMenu);
	}
	
	/* CHANGE */
	public void runGame() {
		this.setupGame();
		this.gameBoard = new GameBoard(this.players, this.openCells, this.closeCells);
		
		Dimension statusDimension = new Dimension(200, this.gameBoard.getFrameHeight());
		Point statusPosition = new Point(this.gameBoard.getFrameWidth(), 0);
		this.gameStatus = new GameStatus(this.players, statusDimension, statusPosition);
		
		this.add(this.gameBoard, BorderLayout.WEST);
		this.add(this.gameStatus, BorderLayout.EAST);
		
		this.pack();
		this.setVisible(true);
		
		while(!this.isFinished()) {
			for(int p = 0; p < nPlayers; p++) {
				this.players[p].playerPlay();
			}
		}
	}
	
	/* CHANGE */
	public void setupFrame() {
		this.setTitle("Ludo King");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setResizable(false);                              
		this.setLocationRelativeTo(null);
	}
	
	public void setupGame() {
		this.openCells = new Cell[OPEN_CELLS];
		this.closeCells = new Cell[MAX_PLAYERS][6];
		this.players = new Player[nPlayers];
		
		this.setupCells();
		this.setupPlayers();
		this.setupFrame();
	}
	
	public void setupCells() {
		for(int p = 0; p < MAX_PLAYERS; p++) {
			this.closeCells[p] = new Cell[6];
			
			for(int c = 0; c < 13; c++) {
				int cellIndex = (p * 13) + c;
				CellColor cellColor = CellColor.White;
				CellType cellType = CellType.Open;
				
				if(c == 0) {
					cellColor = CellColor.values()[cellIndex / 13];
				} else if(c == 8) {
					cellType = CellType.Star;
				}
				
				this.openCells[cellIndex] = new Cell(cellColor, cellType);
			}
			
			for(int c = 0; c < 6; c++) {
				CellColor cellColor = CellColor.values()[p];
				CellType cellType = CellType.Close;
				
				if(c == 5) {
					cellType = CellType.End;
				}
				
				this.closeCells[p][c] = new Cell(cellColor, cellType);
			}
		}
	}
	
	/* CHANGE */
	public void setupPlayers() {
		for(int playerIndex = 0; playerIndex < nPlayers; playerIndex++) {
			Cell[] playerCells = new Cell[57];
			char playerCode = playerCodes[playerIndex];
			CellColor playerColor = CellColor.values()[playerIndex];
			
			for(int c = 0; c <= 50; c++) {
				int cellIndex = ((playerIndex * 13) + c) % 52;
				playerCells[c] = this.openCells[cellIndex];
			}
			
			for(int c = 0; c < 6; c++) {
				int cellIndex = c + 51;
				playerCells[cellIndex] = this.closeCells[playerIndex][c];
			}
			
			this.players[playerIndex] = new Player(this.playerNames[playerIndex], playerCells, playerCode, playerColor);
		}
	}
	
	public void printCells() {
		for(int c = 0; c < OPEN_CELLS; c++) {
			System.out.println("Cell #" + c + ", Color: " + openCells[c].getColor() + ", Type: " + openCells[c].getType());
			
			for(String pawnCode : openCells[c].getPawnCodes()) {
				System.out.println("Pawn Code: " + pawnCode);
			}
		}
		
		System.out.println("-----------");
		
		for(int p = 0; p < nPlayers; p++) {
			for(int c = 0; c < 6; c++) {
				System.out.println("Close Cell #" + playerCodes[p] + c + ", Color: " + closeCells[p][c].getColor() + ", Type: " + closeCells[p][c].getType());
				
				for(String pawnCode : closeCells[p][c].getPawnCodes()) {
					System.out.println("Pawn Code: " + pawnCode);
				}
			}
			
			System.out.println("-----------");
		}
		
		System.out.println();
	}
	
	public static void main(String[] args) {	
		Launcher launcher = new Launcher();
	}
}
