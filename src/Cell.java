import java.util.ArrayList;

public class Cell {
	private ArrayList<Pawn> pawns;
	private final Color cellColor;
	private final CellType cellType;
	
	public Cell() {
		this.pawns = new ArrayList<Pawn>();
		this.cellColor = Color.White;
		this.cellType = CellType.Open;
	}
	
	public Cell(Color cellColor, CellType cellType) {
		this.pawns = new ArrayList<Pawn>();
		this.cellColor = cellColor;
		this.cellType = cellType;
	}
	
	public void addPawn(Pawn pawn) {
		this.pawns.add(pawn);
	}
	
	public void removePawn(Pawn pawn) {
		for(int p = 0; p < this.pawns.size(); p++) {
			if(pawn == this.pawns.get(p)) {
				this.pawns.remove(p);
			}
		}
	}
	
	public ArrayList<Pawn> getPawns() {
		return this.pawns;
	}

	public ArrayList<String> getPawnCodes() {
		ArrayList<String> pawnCodes = new ArrayList<String>();
		
		for(Pawn pawn : this.pawns) {
			pawnCodes.add(pawn.getCode());
		}
		
		return pawnCodes;
	}
	
	public Color getColor() {
		return this.cellColor;
	}
	
	public CellType getType() {
		return this.cellType;
	}
}
