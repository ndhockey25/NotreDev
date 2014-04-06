package com.notredev.snakes;

public abstract class Snake extends Actor {

	private int playerNumber;
	Direction currentDirection;
	private boolean growOnNextMove = false; // Indicates if the snake should grow on his next move
	private boolean isAlive = true;
	
	public Snake(GameBoardCell snakeHeadCell, int playerNumber) {
		super(ActorType.SNAKE);
		addCellBack(snakeHeadCell);
		// TODO: Give snake a body
		
		this.playerNumber = playerNumber;
		
		currentDirection = Direction.RIGHT;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public GameBoardCell getHeadCell() {
		return cells.getFirst();
	}
	
	public void setGrowOnNextMove(boolean growOnNextMove) {
		this.growOnNextMove = growOnNextMove;
	}
	
	protected void move(Direction direction) throws CellOutOfBoundsException {		
		currentDirection = direction;
		GameBoardCell nextCell = gameBoard.getNextCell(getHeadCell(), direction);
		
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
	
	public void die()
	{
		isAlive = false;
		while(!getCells().isEmpty()) {
			removeCellBack();
		}
	}
	
}
