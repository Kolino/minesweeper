package Minesweeper;

import java.util.Scanner;

public class Minesweeper {
	private int[][] gameBoard;
	private int numMines;
	private int numToSelectRemaining;
	private boolean toExit;
	
	public Minesweeper() {
		this.gameBoard = new int[10][10];
		this.numMines = 10;
		this.numToSelectRemaining = 90;
		this.toExit = false;
	}
	
	public Minesweeper(int width, int height, int numMines) {
		this.gameBoard = new int[height][width];
		this.numMines = numMines;
		this.numToSelectRemaining = height * width - numMines;
		this.toExit = false;
	}
	
	public void setupGameBoard() {
		// Placing this.numMines on the game board in random
		// locations
		int numMinesPlaced = 0;
		// Continuously going over the game board until a sufficient
		// number of mines have been placed
		while (numMinesPlaced < this.numMines) {
			for (int y = 0; y < this.gameBoard.length; y++) {
				for (int x = 0; x < this.gameBoard[y].length; x++) {
					// Potentially placing down a mine if there isn't 
					// one there currently and there's still more to be 
					// placed down
					if (this.gameBoard[y][x] == 0 
							&& numMinesPlaced < this.numMines 
							&& Math.random() > 0.95) {
						this.gameBoard[y][x] = -1;
						numMinesPlaced++;
					}
				}
			}
		}
	}
	
	public void displayGameBoard() {
		// Displaying top of game board
		for (int x = 0; x < this.gameBoard[0].length; x++) {
			System.out.print("----");
		}
		System.out.println("-");
			
		
		// Displaying sides of game board, its elements, and
		// lines below each (except for the bottom row)
		for (int y = 0; y < this.gameBoard.length; y++) {
			for (int x = 0; x < this.gameBoard[y].length; x++) {
				if (x == 0)
					System.out.print("|");
				
				if (this.gameBoard[y][x] == -1) {
					System.out.print(" b |");
				} else if (this.gameBoard[y][x] == -2) {
					System.out.print(" x |");
				} else if (this.gameBoard[y][x] > 0) {
					System.out.print(String.format(" %d |", gameBoard[y][x]));
				} else {
					System.out.print("   |");
				}
			}
			System.out.println();
			
			if (y < this.gameBoard.length - 1) {
				for (int i = 0; i < this.gameBoard[y].length; i++) {
					System.out.print("----");
				}
				System.out.println("-");
			}
		}
		
		// Displaying bottom of game board
		for (int x = 0; x < this.gameBoard[0].length; x++) {
			System.out.print("----");
		}
		System.out.println("-");
	}
	
	// TODO: rewrite this function/clean it up
	private int getNumberSurroundingMines(int x, int y) {
		int numSurroundingMines = 0;
		int width = this.gameBoard[0].length;
		int height = this.gameBoard.length;
		
		// Square is a corner square
		// Top left
		if (y == 0 && x == 0) {
			if (this.gameBoard[0][1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[1][1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[1][0] == -1)
				numSurroundingMines++;
		}
		// Top right
		else if (y == 0 && x == width - 1) {
			if (this.gameBoard[0][width - 2] == -1)
				numSurroundingMines++;
			if (this.gameBoard[1][width - 2] == -1)
				numSurroundingMines++;
			if (this.gameBoard[1][width - 1] == -1)
				numSurroundingMines++;
		}
		// Bottom left
		else if (y == height - 1 && x == 0) {
			if (this.gameBoard[height - 2][0] == -1)
				numSurroundingMines++;
			if (this.gameBoard[height - 2][1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[height - 1][1] == -1)
				numSurroundingMines++;
		}
		// Bottom right
		else if (y == height - 1 && x == width - 1) {
			if (this.gameBoard[height - 1][width - 2] == -1)
				numSurroundingMines++;
			if (this.gameBoard[height - 2][width - 2] == -1)
				numSurroundingMines++;
			if (this.gameBoard[height - 2][width - 1] == -1)
				numSurroundingMines++;
		}
		// Square is part of a side but isn't a corner square
		// Left side
		else if (x == 0) {
			if (this.gameBoard[y - 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x] == -1)
				numSurroundingMines++;
		}
		// Top side
		else if (y == 0) {
			if (this.gameBoard[y][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x - 1] == -1)
				numSurroundingMines++;
		}
		// Right side
		else if (x == width - 1) {
			if (this.gameBoard[y - 1][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x - 1] == -1)
				numSurroundingMines++;
		}
		// Bottom side
		else if (y == height - 1) {
			if (this.gameBoard[y][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x + 1] == -1)
				numSurroundingMines++;
		}
		// Square isn't a corner square or part of a side
		else {
			if (this.gameBoard[y - 1][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y - 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x + 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y + 1][x - 1] == -1)
				numSurroundingMines++;
			if (this.gameBoard[y][x - 1] == -1)
				numSurroundingMines++;
		}
		
		return numSurroundingMines;
	}
	
	private void recursiveCascade(int x, int y) {
		// Base case: the given coordinates are invalid
		if (x < 0 || y < 0 || x > this.gameBoard[0].length - 1 
				|| y > this.gameBoard.length - 1)
			return;
			
		// Base case: the given square has already been visited
		if (this.gameBoard[y][x] == -2)
			return;
		
		// Updating the square with the number of surrounding mines
		this.gameBoard[y][x] = this.getNumberSurroundingMines(x, y);
		
		// If there were no surrounding mines, 'blanking out' the
		// square
		if (this.gameBoard[y][x] == 0) {
			this.gameBoard[y][x] = -2;
		}
		
		this.numToSelectRemaining--;

		// Base case: given square has at least one mine
		// surrounding it
		if (this.gameBoard[y][x] > 0) {
			return;
		}
		
		// Recursive case: the given square is not surrounded by any
		// mines. Recursing into all the squares that surround it
		this.recursiveCascade(x - 1, y);
		this.recursiveCascade(x - 1, y - 1);
		this.recursiveCascade(x, y - 1);
		this.recursiveCascade(x + 1, y - 1);
		this.recursiveCascade(x + 1, y);
		this.recursiveCascade(x + 1, y + 1);
		this.recursiveCascade(x, y + 1);
		this.recursiveCascade(x - 1, y + 1);
	}
	
	public boolean placeCommand(int x, int y) {
		// The chosen square is a mine
		if (this.gameBoard[y][x] == -1)
			return false;
		
		// Filling in this mine and then recursively filling in any 
		// mines 'connected' to it
		recursiveCascade(x, y);
		
		return true;
	}
	
	public static String[] getSplitCommand(String command) {
		String trimmedCommand = command.trim();
		
		return trimmedCommand.split("\\s+");
	}
	
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isValidCommand(String command, Minesweeper game) {
		if (command == null)
			throw new Error("Given command was null.");
		
		if (command == "")
			return false;
		
		String[] splitCommand = getSplitCommand(command);
		
		if (splitCommand.length == 1) {
			// DISPLAY
			if (splitCommand[0].equals("DISPLAY"))
				return true;
			
			// HELP
			if (splitCommand[0].equals("HELP"))
				return true;
			
			// QUIT
			if (splitCommand[0].equals("QUIT"))
				return true;
		}
		
		// START 
		if (splitCommand[0].equals("START")) {
			if (splitCommand.length == 1)
				return true;
			
			// Checking given width, height, and number of mines
			if (splitCommand.length == 4) {
				if (!isInt(splitCommand[1]) 
						|| !isInt(splitCommand[2]) 
						|| !isInt(splitCommand[3]))
					return false;
				
					if (Integer.parseInt(splitCommand[1]) < 0 
							|| Integer.parseInt(splitCommand[2]) < 0 
							|| Integer.parseInt(splitCommand[3]) < 0)
						return false;
					
					// More mines were given than can fit on the proposed game board
					if (Integer.parseInt(splitCommand[3]) >
							Integer.parseInt(splitCommand[1]) * Integer.parseInt(splitCommand[2]))
						return false;
				
				return true;
			}
			
			return false;
		}
		
		// PLACE
		if (splitCommand[0].equals("PLACE")) {
			if (splitCommand.length != 3) 
				return false;
			
			if (!isInt(splitCommand[1]) || !isInt(splitCommand[2]))
				return false;
			
			if (game != null) {
				int x = Integer.parseInt(splitCommand[1]);
				int y = Integer.parseInt(splitCommand[2]);
				
				if (x < 0 || x > game.gameBoard[0].length - 1)
					return false;
				
				if (y < 0 || y > game.gameBoard.length - 1)
					return false;
				
				// Given square has already been tried
				if (game.gameBoard[y][x] == -2)
					return false;
			}
			
			return true;
		}
		
		// Invalid command
		return false;
	}
	
	public static Minesweeper readCommand(String command, Minesweeper game) {
		// Invalid command
		if (!isValidCommand(command, game)) {
			System.out.println("Invalid command given.");
		} else {
			String[] splitCommand = getSplitCommand(command);
			
			// HELP
			if (splitCommand[0].equals("HELP")) {
				System.out.println("'DISPLAY' displays the current state of the game board currently.");
				System.out.println("'START width height' initialises the game. If 'START' is all that's given, a 10x10 grid with 10 mines is used.");
				System.out.println("'PLACE x y' tries the square at coordinates (x, y) on the game board.");
				System.out.println("'QUIT' ends the game.");
			}
			
			// DISPLAY
			if (splitCommand[0].equals("DISPLAY"))
				if (game == null)
					System.out.println("Game isn't running. Please start game!");
				else 
					game.displayGameBoard();
			
			// START
			if (splitCommand[0].equals("START"))
				if (game != null) {
					System.out.println("Game is already running.");
				} else {
					Minesweeper newGame = null;
					if (splitCommand.length == 1) {
						newGame = new Minesweeper();
					} else {
						newGame = new Minesweeper(Integer.parseInt(splitCommand[1]), 
								Integer.parseInt(splitCommand[2]), 
								Integer.parseInt(splitCommand[3]));
					}
					newGame.setupGameBoard();
					System.out.println("Game started.");
					return newGame;
				}
			
			// PLACE
			if (splitCommand[0].equals("PLACE"))
				if (game == null) {
					System.out.println("Game isn't running. Please start game!");
				} else {
					if (!game.placeCommand(Integer.parseInt(splitCommand[1]), 
							Integer.parseInt(splitCommand[2]))) {
						System.out.println("Boom!");
						game.toExit = true;
					} else {
						System.out.println("Placed.");
					}
				}
					
			
			// QUIT
			if (splitCommand[0].equals("QUIT")) {
				System.out.println("Exiting");
				game.toExit = true;
			}
		}
		
		return game;
	}
	

	public static void main(String[] args) {
//		Minesweeper testGame = new Minesweeper();
//		testGame.setupGameBoard();
//		testGame.displayGameBoard();
		
		System.out.println("Welcome to my Minesweeper game. Please enter a command or enter 'HELP' if you're unsure.");
		
		// Continuously reading commands
		Minesweeper game = null;
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String input = sc.nextLine().replaceAll("\n", "");
			
			game = readCommand(input, game);
			
			if (game != null && game.numToSelectRemaining == 0) {
				game.toExit = true;
				System.out.println("Congratulations! You won!");
			}
			
			if (game != null && game.toExit)
				break;
		}
	}

}
