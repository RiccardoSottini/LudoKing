public class Pawn {
	private final Player player;
	
	private final Color pawnColor;
	private boolean pawnStatus = false;
	private int pawnPosition = 0;
	
	private final char playerCode;
	private final int pawnCode;
	
	public Pawn(Player player, int pawnCode, Color pawnColor) {
		this.player = player;
		this.playerCode = player.getCode();
		this.pawnCode = pawnCode;
		
		this.pawnColor = pawnColor;
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
			if(this.pawnPosition == 0 && changePosition == 6) {
				this.setStatus(true);
			}
		} else if(newPosition <= 56) {
			this.pawnPosition = newPosition;
			
			if(this.pawnPosition == 56) {
				this.setStatus(false);
			}
		}
		
		return this.pawnPosition;
	}
	
	public int getPosition() {
		return this.pawnPosition;
	}
	
	public String getCode() {
		return this.playerCode + Integer.toString(this.pawnCode);
	}
}
