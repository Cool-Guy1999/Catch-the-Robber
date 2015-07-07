public class Main {

	public static final int NUMBER_OF_GAMES = 10; //Number of games to play
	public static final long MAX_TURN_TIME = 200*1000000; // Nanoseconds per Turn (200ms)
	public static final int sleepTime = 750; //Time to wait between turns (in ms)
	
	private int robberTotal = 0;
	private int copTotal    = 0; 
	private int robberMoves = 0;
	private int copMoves    = 0; 
	private int gameNo      = 1;

	Main(Cop c, Robber r) {
		System.out.println("Starting game " + c.getName() + " vs " + r.getName());
		playGame(c, r);
	}
	
	private void playGame(Cop c, Robber r) {
	
		while(gameNo <= NUMBER_OF_GAMES){
			Grid grid = new Grid();

			while(true) {
			
			long startTurnTime = System.nanoTime();//Measure time
			
			long startTurnTime = System.nanoTime();//Measure time
				
			int move1 = c.takeTurn(grid);
				
			if(System.nanoTime() - startTurnTime > MAX_TURN_TIME) {
				System.out.println(c.getName() + " has been disqualified for taking more than 200 ms");
				System.exit(-1);
			}
			
			if(grid.isValidMove(grid, move1)){
				copMoves++;
				if(grid.checkWinner(move1, 0))
					break;
				if(c.placeTrap && move1 != 0 && c.TRAPS > 0){
					grid.updateGrid(move1, 0);
					grid.placeTrap(move1);
					c.TRAPS--;
				}else{
					if(c.placeTrap && move1 == 0)
						System.out.println("You can't place a trap and just stand in there!");
					else if(c.placeTrap && c.TRAPS <= 0)
						System.out.println("You are out of traps");
					c.placeTrap = false;
				}
			}
			
			grid.printGrid();
			try{Thread.sleep(sleepTime);}catch(InterruptedException e){System.out.println("Sleep INTERRUPTED!");System.exit(1);}
							
			startTurnTime = System.nanoTime();//Measure time
							
			int move2 = r.takeTurn(grid);
				
			if(System.nanoTime() - startTurnTime > MAX_TURN_TIME) {
				System.out.println(r.getName() + " has been disqualified for taking more than 200 ms");
				System.exit(-1);
			}
				
			if(grid.isValidPosition(move2)) {
				robberMoves++;
				if(grid.checkWinner(move2, 1)) {
					break;
				}
				grid.updateGrid(move2, 1);
			}
				
			grid.printGrid();
			try{Thread.sleep(sleepTime);}catch(InterruptedException e){System.out.println("Sleep INTERRUPTED!");System.exit(1);}
			
			grid.printGrid();
				
			printScores();
			gameNo++;
				
			robberTotal += robberMoves;
			copTotal += copMoves;
				
			robberMoves=copMoves=0;
		}
			
		System.out.println("\n\nFINAL SCORES:\n");
		System.out.println("Cop("+c.getName()+") = " + copTotal);
		System.out.println("Robber("+r.getName()+") = " + robberTotal);
	}
	
	private void printScores() {
		System.out.println("Game #" + gameNo + " scores:");
		System.out.println("Score of the robber = " + robberMoves);
		System.out.println("Score of the cop = " + copMoves);
	}
}
