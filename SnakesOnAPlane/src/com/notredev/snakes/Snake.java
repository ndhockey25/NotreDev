package com.notredev.snakes;

import java.util.LinkedList;
import java.util.ListIterator;


public abstract class Snake extends Actor {

	GameBoard gameBoard = GameBoard.Instance();
	int playerNumber;
	Direction currentDirection;
	boolean growOnNextMove = false; // Indicates if the snake should grow on his next move
	
	public Snake(GameBoardCell snakeHeadGameBoardCell, int playerNumber) {
		super(ActorType.SNAKE, null);
		LinkedList<GameBoardCell> bodyParts = new LinkedList<GameBoardCell>();
		bodyParts.add(snakeHeadGameBoardCell);
		// TODO: Give snake a body
		setActorCells(bodyParts);
		
		playerNumber = this.playerNumber;
		
		currentDirection = Direction.RIGHT;
	}
	
	public abstract void update();
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public GameBoardCell getHeadGameCell() {
		return getActorCells().getFirst();
	}
	
	protected void move(Direction direction) {		
		int nextRow = getHeadGameCell().positionX;
		int nextColumn = getHeadGameCell().positionY;
		
		currentDirection = direction;

		switch (direction) {
        	case UP:
        		nextRow--;
        		break;
        	case DOWN:
        		nextRow++;
        		break;
        	case LEFT:
        		nextColumn--;
        		break;
        	case RIGHT:
        		nextColumn++;
        		break;
		}
		
		GameBoardCell nextCell = gameBoard.getCell(nextRow, nextColumn);
		
		LinkedList<GameBoardCell> body = getActorCells();
		body.addFirst(nextCell);

		if (growOnNextMove) {
			growOnNextMove = false;
		}
		else {
			body.pop();
		}
		
		setActorCells(body);
	}
	
	public Obstacle split(GameBoardCell splitCell) {
		LinkedList<GameBoardCell> obstacleCells = new LinkedList<GameBoardCell>();
		
		ListIterator<GameBoardCell> snakeIterator = getActorCells().listIterator();
		while(snakeIterator.hasNext()) {
			GameBoardCell cell = snakeIterator.next();
			if (cell == splitCell) {
				snakeIterator.remove(); // Remove the cell that we are splitting on
				break;
			}
		}
		
		while(snakeIterator.hasNext()) {
			GameBoardCell cell = snakeIterator.next();
			obstacleCells.add(cell); // Copy each cell of the snake's tail to the obstacle
			snakeIterator.remove(); // Remove it from the snake
		}
		
		return new Obstacle(obstacleCells);
	}
	
}
