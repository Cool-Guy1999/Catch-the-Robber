public class Main {

	public static final int NUMBER_OF_GAMES = 10; //Number of games to play
	public static final long MAX_TURN_TIME = 200*1000000; // Nanoseconds per Turn (200ms)
	public static final int sleepTime = 100; //Time to wait between turns
	
	private int robberMoves = 0;
	private int copMoves    = 0; 

	Main(Cop c, Robber r) {
		System.out.println("Starting game " + c.getName() + " vs " + r.getName());
		playGame(c, r);
	}
	
	private void playGame(Cop c, Robber r) {
		Grid grid = new Grid();
		
		boolean copWinner = false;
		
		while(true) {
		
			Grid clone = new Grid(grid);
			
			long startTurnTime = System.nanoTime();//Measure time
			
			int move1 = c.takeTurn();
			
			if(System.nanoTime() - startTurnTime > MAX_TURN_TIME) {
				System.out.println(c.getName() + " has been disqualified for taking more than 200 ms");
				System.exit(-1);
			}
			
			if(grid.isValidMove(grid, move1)){
				copMoves++;
				if(grid.checkWinner(move1, 0))
					break;
			}
			
			grid.printGrid();
			try{Thread.sleep(sleepTime);}catch(InterruptedException e){System.out.println("Sleep INTERRUPTED!");System.exit(1);}
						
			startTurnTime = System.nanoTime();//Measure time
						
			int move2 = r.takeTurn();
			
			if(System.nanoTime() - startTurnTime > MAX_TURN_TIME) {
				System.out.println(r.getName() + " has been disqualified for taking more than 200 ms");
				System.exit(-1);
			}
			
			if(grid.isValidPosition(grid, move2)) {
				robberMoves++;
				if(grid.checkWinner(move2, 1)) {
					break;
				}
				grid.updateGrid(move2, 1);
			}
			
			grid.printGrid();
			try{Thread.sleep(sleepTime);}catch(InterruptedException e){System.out.println("Sleep INTERRUPTED!");System.exit(1);}
		}
		
		grid.printGrid();
		System.out.println("Game ended!");
		
		printScores();
	}
	
	private void printScores() {
		System.out.println("Score of the robber = " + robberMoves);
		System.out.println("Score of the cop = " + copMoves);
	}
}
