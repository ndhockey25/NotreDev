package com.notredev.snakes;

import java.util.LinkedList;
import java.util.ListIterator;

import android.graphics.Path.Direction;

public class Snake extends Actor {

	InputManager inputManager = InputManager.Instance();
	GameBoard gameBoard = new GameBoard();
	Direction currentDirection;
	boolean growOnNextMove = false; // Indicates if the snake should grow on his next move
	int playerNumber;
	
	public Snake(GameBoardCell snakeHeadGameBoardCell, int playerNumber) {
		super(ActorType.SNAKE, null);
		LinkedList<GameBoardCell> bodyParts = new LinkedList<GameBoardCell>();
		bodyParts.add(snakeHeadGameBoardCell);
		// TODO: Give snake a body
		setActorCells(bodyParts);
		
		playerNumber = this.playerNumber;
	}
	
	public GameBoardCell getHeadGameCell() {
		return getActorCells().getFirst();
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
	
	/**
	 * Returns true if the snake is still alive
	 */
	public boolean validateMove() {
		GameBoardCell headCell = getHeadGameCell();
		Collection<GameBoadCell> cellActors = headCell.getActors();
		for (Actor actor : cellActors) {
			if (actor.getType() == ActorType.FOOD) {
				growOnNextMove = true;
			}
			elif (actor.getType() == ActorType.SNAKE) {
				
			}
		}
		
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
