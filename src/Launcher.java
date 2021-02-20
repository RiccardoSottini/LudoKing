import java.util.ArrayList;
import java.util.Random;


enum Color {
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

public class Launcher {
	private final char[] playerCodes = {'B', 'R', 'G', 'Y'};
	
	private final int MAX_PLAYERS = 4;
	private final int OPEN_CELLS = 52;

	private Cell[] openCells;
	private Cell[][] closeCells;
	
	private int nPlayers;
	private Player[] players;
	
	public Launcher(int nPlayers) {
		this.openCells = new Cell[OPEN_CELLS];
		this.closeCells = new Cell[MAX_PLAYERS][6];
		
		this.nPlayers = nPlayers;
		this.players = new Player[nPlayers];
		
		this.setupCells();
		this.setupPlayers();
		
		for(int d = 0; d < 60; d++) {
			int diceValue = this.players[d % 4].playDice().getValue();
			this.players[d % 4].playMove(0);
			
			System.out.println("Roll Dice: #" + d + ", " + diceValue);
			System.out.println("Player: " + playerCodes[d % 4]);
			
			for(int c = 0; c < OPEN_CELLS; c++) {
				System.out.println("Cell #" + c + ", Color: " + openCells[c].getColor() + ", Type: " + openCells[c].getType());
				
				for(String pawnCode : openCells[c].getPawnCodes()) {
					System.out.println("Pawn Code: " + pawnCode);
				}
			}
			
			System.out.println();
		}
		
		
		for(int c = 0; c < OPEN_CELLS; c++) {
			System.out.println("Cell #" + c + ", Color: " + openCells[c].getColor() + ", Type: " + openCells[c].getType());
		
			for(String pawnCode : openCells[c].getPawnCodes()) {
				System.out.println("Pawn Code: " + pawnCode);
			}
		}
		
		for(int p = 0; p < nPlayers; p++) {
			System.out.println();
			System.out.println("Player #" + (p + 1) + ":");
			
			for(int c = 0; c < 6; c++) {
				System.out.println("Cell #" + c + ", Color: " + closeCells[p][c].getColor() + ", Type: " + closeCells[p][c].getType());
			}
		}
	}
	
	public void setupCells() {
		for(int p = 0; p < MAX_PLAYERS; p++) {
			this.closeCells[p] = new Cell[6];
			
			for(int c = 0; c < 13; c++) {
				int cellIndex = (p * 13) + c;
				Color cellColor = Color.White;
				CellType cellType = CellType.Open;
				
				if(c == 0) {
					cellColor = Color.values()[cellIndex / 13];
				} else if(c == 8) {
					cellType = CellType.Star;
				}
				
				this.openCells[cellIndex] = new Cell(cellColor, cellType);
			}
			
			for(int c = 0; c < 6; c++) {
				Color cellColor = Color.values()[p];
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
			Color playerColor = Color.values()[p];
			
			for(int c = 0; c <= 50; c++) {
				int cellIndex = ((p * 13) + c) % 52;
				playerCells[c] = this.openCells[cellIndex];
			}
			
			for(int c = 0; c < 6; c++) {
				int cellIndex = c + 51;
				playerCells[cellIndex] = this.closeCells[p][c];
			}
			
			this.players[p] = new Player(playerCells, playerCode, playerColor);
		}
	}
	
	public static void main(String[] args) {
		int nPlayers = 4;
		
		Launcher launcer = new Launcher(nPlayers);
	}
}
