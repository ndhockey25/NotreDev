package com.notredev.snakes;

public abstract class Snake extends Actor {

	int playerNumber;
	Direction currentDirection;
	boolean growOnNextMove = false; // Indicates if the snake should grow on his next move
	
	public Snake(GameBoardCell snakeHeadGameBoardCell, int playerNumber) {
		super(ActorType.SNAKE);
		addCellBack(snakeHeadGameBoardCell);
		// TODO: Give snake a body
		
		this.playerNumber = playerNumber;
		
		currentDirection = Direction.RIGHT;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public GameBoardCell getHeadGameCell() {
		return cells.getFirst();
	}
	
	protected void move(Direction direction) throws CellOutOfBoundsException {		
		currentDirection = direction;
		GameBoardCell nextCell = gameBoard.getNextCell(getHeadGameCell(), direction);
		
		addCellFront(nextCell);

		if (growOnNextMove) {
			growOnNextMove = false;
		}
		else {
			removeCellBack();
		}
	}
	
	public Obstacle split(GameBoardCell splitCell) {
		Obstacle obstacle = new Obstacle();
		
		if (cells.contains(splitCell)) {
			for (int i = cells.size() - 1; i >= 0; i--) { // Reverse loop
				GameBoardCell removedCell = removeCellBack(); // Remove cell from snake
				obstacle.addCellBack(removedCell); // Add cell to obstacle
				if (cells.get(i) == splitCell) {
					break;
				}
			}
		}
		
		return obstacle;
	}
	
}
