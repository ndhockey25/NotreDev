package com.notredev.snakes;

import java.util.LinkedList;
import java.util.ListIterator;

public class Snake extends Actor {

	InputManager inputManager = InputManager.Instance();
	GameBoard gameBoard = new GameBoard();
	
	Direction currentDirection;
	
	public Snake(GameBoardCell snakeHeadGameBoardCell) {
		super(ActorType.SNAKE, null);
		LinkedList<GameBoardCell> bodyParts = new LinkedList<GameBoardCell>();
		bodyParts.add(snakeHeadGameBoardCell);
		// TODO: Give snake a body
		setActorCells(bodyParts);
	}
	
	public GameBoardCell getHeadGameCell() {
		return getActorCells().getFirst();
	}
	
	/**
	 * Move in the current direction
	 */
	public void move() {
		move(currentDirection);
	}
	
	public void move(Direction direction) {		
		int nextRow = getHeadGameCell().positionX;
		int nextColumn = getHeadGameCell().positionY;
		
		currentDirection = direction;
		
		switch (direction) {
        	case UP:
        		nextRow = getHeadGameCell().positionX - 1;
        		nextColumn = getHeadGameCell().positionY;
        		break;
        	case DOWN:
        		nextRow = getHeadGameCell().positionX + 1;
        		nextColumn = getHeadGameCell().positionY;
        		break;
        	case LEFT:
        		nextRow = getHeadGameCell().positionX;
        		nextColumn = getHeadGameCell().positionY - 1;
        		break;
        	case RIGHT:
        		nextRow = getHeadGameCell().positionX;
        		nextColumn = getHeadGameCell().positionY + 1;
        		break;
		}
		
		GameBoardCell nextCell = gameBoard.getCell(nextRow, nextColumn);
		
		// TODO: Adjust LinkedList with new cells
	}
	
	public Direction getDirection(InputState state) {
		if (!(state.Up() ^ state.Down() ^ state.Left() ^ state.Right())) {
			// If multiple directions are pressed, continue in the current direction
			return currentDirection; 
		}
		if (state.Up()) {
			return Direction.UP;
		}
		if (state.Down()) {
			return Direction.DOWN;
		}
		if (state.Left()) {
			return Direction.LEFT;
		}
		if (state.Right()) {
			return Direction.RIGHT;
		}
		return currentDirection; // Default to the direction the snake is already moving
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
