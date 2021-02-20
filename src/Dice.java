import java.util.Random;

public class Dice {
	private int value;
	
	public Dice() { 
		this.rollDice();
	}
	
	public Dice(int value) {
		this.value = value;
	}
	
	public void rollDice() {
		Random rnd = new Random();
		this.setValue(rnd.nextInt(6) + 1);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
