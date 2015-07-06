import java.util.Random;

public class RandCop implements Cop {

	public String getName() {
		return "RandCop";
	}
	
	public int takeTurn() {
		Random rand = new Random();
		int turn = rand.nextInt(directions.values().length);
		return turn;
	}
}
