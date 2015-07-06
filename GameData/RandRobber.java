import java.util.Random;

public class RandRobber implements Robber {
	public String getName() {
		return "RandRobber";
	}
	
	public int takeTurn() {
		Random rand = new Random();
		int turn = rand.nextInt(directions.values().length);
		return turn;
	}
}
