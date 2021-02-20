import java.util.ArrayList;
import java.util.Random;

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
		
		GameField gameField = new GameField(openCells, closeCells);
		
		/*Pawn p1 = new Pawn(players[0], 0, CellColor.Blue);
		Pawn p2 = new Pawn(players[1], 0, CellColor.Red);
		Pawn p3 = new Pawn(players[2], 0, CellColor.Green);
		Pawn p4 = new Pawn(players[3], 0, CellColor.Yellow);
		
		openCells[1].addPawn(p1);
		openCells[1].addPawn(p2);
		openCells[1].addPawn(p3);
		openCells[1].addPawn(p4);*/
		
		for(int d = 0; d < 300; d++) {
			for(int p = 0; p < nPlayers; p++) {
				int nDice = (d * nPlayers) + p;
				int diceValue = this.players[p].playDice().getValue();
				
				Random rnd = new Random();
				
				int n = rnd.nextInt(4);
				
				this.players[p].playMove(n);
				
				/*System.out.println("Roll Dice: #" + nDice + ", " + diceValue);
				System.out.println("Player: " + playerCodes[p]);
				
				this.printCells();*/
				
				try {
				    Thread.sleep(100);
				} catch (Exception e) {
				    e.printStackTrace();
				}
			}
		}
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
			
			this.players[p] = new Player(playerCells, playerCode, playerColor);
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
		int nPlayers = 4;
		
		Launcher launcer = new Launcher(nPlayers);
	}
}
