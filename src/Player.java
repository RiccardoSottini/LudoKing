import java.util.ArrayList;

public class Player {
	private final Cell[] playerCells;
	private final char playerCode;
	private final Color playerColor;
	private boolean playerWon;
	
	private final Pawn[] pawns;
	
	private Dice dice = null;
	
	public Player(Cell[] playerCells, char playerCode, Color playerColor) {
		this.playerCells = playerCells;
		this.playerCode = playerCode;
		this.playerColor = playerColor;
		this.playerWon = false;
		
		this.pawns = new Pawn[4];
		
		for(int p = 0; p < 4; p++) {
			this.pawns[p] = new Pawn(this, p, playerColor);
		}
	}
	
	public Dice playDice() {
		return this.dice = new Dice();
	}
	
	public void playMove(int pawnIndex) {
		if(dice != null) {
			Pawn selectedPawn = this.pawns[pawnIndex];
			int changePosition = this.dice.getValue();
			
			selectedPawn.movePawn(changePosition);
		}
		
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
