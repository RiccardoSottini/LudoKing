public class Pawn {
	private final int pawnCode;
	private final Color pawnColor;
	private boolean pawnStatus;
	private int pawnPosition;
	
	private final Player player;
	private final char playerCode;
	
	public Pawn(Player player, int pawnCode, Color pawnColor) {
		this.pawnCode = pawnCode;
		this.pawnColor = pawnColor;
		this.pawnStatus = false;
		this.pawnPosition = -1;
		
		this.player = player;
		this.playerCode = player.getCode();
	}
	
	public boolean movePawn(int changePosition) {
		if(this.isPossible(changePosition)) {
			int oldPosition = this.getPosition();
			int newPosition = this.addPosition(changePosition);
			
			Cell newCell = player.getCell(newPosition);
			Cell oldCell = player.getCell(oldPosition);
			
			if(newCell.canKill()) {
				newCell.killPawns(this);
			}
			
			newCell.addPawn(this);
			
			if(oldCell != null) {
				oldCell.removePawn(this);
			}
			
			return true;
		}
		
		return false;
	}
	
	public void setDead() {
		this.setStatus(false);
		this.setPosition(-1);
	}
	
	public boolean isPossible(int changePosition) {
		int newPosition = this.pawnPosition + changePosition;
		
		if(this.getStatus() == false) {
			return changePosition == 6 && newPosition <= 56;
		}
		
		return true;
	}
	
	public void setStatus(boolean pawnStatus) {
		this.pawnStatus = pawnStatus;
	}
	
	public boolean getStatus() {
		return this.pawnStatus;
	}
	
	public int addPosition(int changePosition) {
		int newPosition = this.pawnPosition + changePosition;
		
		if(this.getStatus() == false) {
			if(this.getPosition() == -1 && changePosition == 6) {
				this.setPosition(0);
				this.setStatus(true);
			}
		} else if(newPosition <= 56) {
			this.pawnPosition = newPosition;
			
			if(this.getPosition() == 56) {
				this.setStatus(false);
			}
		}
		
		return this.pawnPosition;
	}
	
	public void setPosition(int newPosition) {
		this.pawnPosition = newPosition;
	}
	
	public int getPosition() {
		return this.pawnPosition;
	}
	
	public String getCode() {
		return this.playerCode + Integer.toString(this.pawnCode);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public char getPlayerCode() {
		return this.playerCode;
	}
}
