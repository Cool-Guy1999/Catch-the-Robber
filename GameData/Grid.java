public class Grid {

	private final int SIZE = 7;
	private int[][] grid;
	private static final int EMPTY  = 0; 
	private static final int TRAP   = -1;
	private static final int ROBBER = 1;
	private static final int COP    = 2;
	
	/**
	 * Constructor initializes the grid
	 */
	public Grid() {
		grid = new int[SIZE][SIZE];
		
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				grid[i][j]=EMPTY;
				
		grid[0][3] = ROBBER;
		grid[6][3] = COP;
	}
	
	/**
	 * Prints the grid
	 */
	public void printGrid() {
	
        for(int i=0; i<SIZE*4 + 1; i++)
        {
            System.out.print("=");
        }

        System.out.print("\n");

        for(int i=0; i < SIZE; i++) {
            for(int j=0; j < SIZE; j++) {
                System.out.print("| ");
                if(grid[i][j] == EMPTY)
					System.out.print(" ");
				else if(grid[i][j] == ROBBER)
					System.out.print("R");
				else if(grid[i][j] == COP)
					System.out.print("C");
				else if(grid[i][j] == TRAP)
					System.out.print("T");
				else if(grid[i][j] == 'O')
					System.out.print("O");
				else{
					System.err.println("AN ERROR OCCURRED. UNEXPECTED ITEM IN GRID"+grid[i][j]+". BAILING OUT!");
					System.exit(-1);
				}
				System.out.print(" ");
            }
            System.out.println("|");
            for(int j=0; j < SIZE*4 + 1; j++)
                System.out.print("=");
            System.out.print("\n");
        }  
	}
	
	/**
	 * Checks if the given move (cop) is valid
	 */
	public boolean isValidMove(int direction){
		if(direction < 0 || direction > 4)
				return false;
				
		int[] pos = findCop(grid);
		
		if(direction == 1 && ( pos[0]-1 < 0 || grid[pos[0]-1][pos[1]] == TRAP) ) //UP
			return false;
		if(direction == 2 && ( pos[1]+1 > 6 || grid[pos[0]][pos[1]+1] == TRAP) ) //RIGHT
			return false;
		if(direction == 3 && ( pos[0]+1 > 6 || grid[pos[0]+1][pos[1]] == TRAP) ) //DOWN
			return false;
		if(direction == 4 && ( pos[1]-1 < 0 || grid[pos[0]][pos[1]-1] == TRAP) ) //LEFT
			return false;
		return true;
	}
	
	/**
	 * Checks if the given move (robber) is valid
	 */
	public boolean isValidPosition(int direction){
		if(direction < 0 || direction > 4)
				return false;
				
		int[] pos = findRobber(grid);
		if(direction == 1 && pos[0]-1 < 0) //UP
			return false;
		if(direction == 2 && pos[1]+1 > 6) //RIGHT
			return false;
		if(direction == 3 && pos[0]+1 > 6) //DOWN
			return false;
		if(direction == 4 && pos[1]-1 < 0) //LEFT
			return false;
		return true;
	}
	
	/**
	 * Finds the position of the Cop.
	 */
	public int[] findCop(int[][] grid) {
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				if(grid[i][j] == COP)
					return new int[]{i, j};
		System.err.println("ERROR: Cop is missing. BAILING OUT!");
		System.exit(-1);
		
		return null; //Shut up compiler!
	}
	
	/**
	 * Finds the position of the Robber.
	 */
	public int[] findRobber(int[][] grid) {
		for(int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				if(grid[i][j] == ROBBER)
					return new int[]{i, j};
		System.err.println("ERROR: Robber is missing. BAILING OUT!");
		System.exit(-1);
		
		return null; //Shut up compiler!
	}
	
	/**
	 * Updates the position of the cop and/or robber
	 */
	public void updateGrid(int direction, int player) {
		int[] pos;
		if(direction != 0){
			if(player == 0) //COP
			{
				pos = findCop(grid);
				grid[pos[0]][pos[1]] = EMPTY;
				if(direction == 1)
					grid[pos[0]-1][pos[1]] = COP;
				else if(direction == 2)
					grid[pos[0]][pos[1]+1] = COP;
				else if(direction == 3)
					grid[pos[0]+1][pos[1]] = COP;
				else if(direction == 4)
					grid[pos[0]][pos[1]-1] = COP;
			} else if(player == 1) 
			{
				pos = findRobber(grid);
				grid[pos[0]][pos[1]] = EMPTY;
				if(direction == 1)
					grid[pos[0]-1][pos[1]] = ROBBER;
				else if(direction == 2)
					grid[pos[0]][pos[1]+1] = ROBBER;
				else if(direction == 3)
					grid[pos[0]+1][pos[1]] = ROBBER;
				else if(direction == 4)
					grid[pos[0]][pos[1]-1] = ROBBER;

			} else {
				System.err.println("Unexpected error (`updateGrid` called with invalid second argument '"+player+"') . BAILING OUT!");
				System.exit(-1);
			}
		}
	}
	
	/*
	 * Checks the winner
	 */
	public boolean checkWinner(int direction, int player) {
		if(direction == 0)
			return false;
			
		int[] pos;
		if(player == 0) {
			pos = findCop(grid);
			
			if(direction == 1 && grid[pos[0]-1][pos[1]] == ROBBER){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]-1][pos[1]] = 'O';
				return true;
			}
			else if(direction == 2 && grid[pos[0]][pos[1]+1] == ROBBER){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]][pos[1]+1] = 'O';
				return true;
			}
			else if(direction == 3 && grid[pos[0]+1][pos[1]] == ROBBER){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]+1][pos[1]] = 'O';
				return true;
			}
			else if(direction == 4 && grid[pos[0]][pos[1]-1] == ROBBER){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]][pos[1]-1] = 'O';
				return true;
			}
		}
		else if(player == 1) {
			pos = findRobber(grid);
			
			if(direction == 1 && grid[pos[0]-1][pos[1]] == COP){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]-1][pos[1]] = 'O';
				return true;
			}
			else if(direction == 2 && grid[pos[0]][pos[1]+1] == COP){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]][pos[1]+1] = 'O';
				return true;
			}
			else if(direction == 3 && grid[pos[0]+1][pos[1]] == COP){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]+1][pos[1]] = 'O';
				return true;
			}
			else if(direction == 4 && grid[pos[0]][pos[1]-1] == COP){
				grid[pos[0]][pos[1]] = EMPTY;
				grid[pos[0]][pos[1]-1] = 'O';
				return true;
			}
		} else {
				System.err.println("Unexpected error (`checkWinner` called with invalid second argument '"+player+"') . BAILING OUT!");
				System.exit(-1);
			}
			
		return false;
	}
	
	/*
	 * Places a trap in the grid (This method never gets called, currently)
	 */
	public void placeTrap(int direction){
		int[] pos = findCop(grid);
		if(direction == 1) {
			grid[pos[0]+1][pos[1]] = TRAP;
		}else if(direction == 2) {
			grid[pos[0]][pos[1]-1] = TRAP;
		}else if(direction == 3) {
			grid[pos[0]-1][pos[1]] = TRAP;
		}else if(direction == 4) {
			grid[pos[0]][pos[1]+1] = TRAP;
		}
	}
}
