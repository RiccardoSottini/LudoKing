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
	
	private GameBoard gameBoard;
	private GameStatus gameStatus;
	
	public Launcher(int nPlayers) {	
		this.openCells = new Cell[OPEN_CELLS];
		this.closeCells = new Cell[MAX_PLAYERS][6];
		
		this.nPlayers = nPlayers;
		this.players = new Player[nPlayers];
		
		this.setupCells();
		this.setupPlayers();
		this.setupFrame();
		
		while(!this.isFinished()) {
			for(int p = 0; p < nPlayers; p++) {
				this.players[p].playerPlay();
			}
		}
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
	
	public void setupFrame() {
		this.setTitle("Ludo King");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setResizable(false);                              
		this.setLocationRelativeTo(null);
		
		this.gameBoard = new GameBoard(this.players, this.openCells, this.closeCells);
		
		Dimension statusSize = new Dimension(200, this.gameBoard.getFrameHeight());
		Point statusPosition = new Point(this.gameBoard.getFrameWidth(), 0);
		this.gameStatus = new GameStatus(this.players, statusSize, statusPosition);
		this.add(this.gameBoard, BorderLayout.WEST);
		this.add(this.gameStatus, BorderLayout.EAST);
		
		this.pack();
		this.setVisible(true);
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
	
	public void setupPlayers() {
		for(int p = 0; p < nPlayers; p++) {
			Cell[] playerCells = new Cell[57];
			char playerCode = playerCodes[p];
			CellColor playerColor = CellColor.values()[p];
			
			for(int c = 0; c <= 50; c++) {
				int cellIndex = ((p * 13) + c) % 52;
				playerCells[c] = this.openCells[cellIndex];
			}
			
			for(int c = 0; c < 6; c++) {
				int cellIndex = c + 51;
				playerCells[cellIndex] = this.closeCells[p][c];
			}
			
			this.players[p] = new Player("test", playerCells, playerCode, playerColor);
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
		int nPlayers = 2;
		
		Launcher launcher = new Launcher(nPlayers);
	}
}
