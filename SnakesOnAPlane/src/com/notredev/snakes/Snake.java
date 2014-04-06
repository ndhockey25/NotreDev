package com.notredev.snakes;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class Snake extends Actor {

	InputManager inputManager = InputManager.Instance();
	GameBoard gameBoard = new GameBoard();
	
	Direction direction;
	
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
	
	public void move(InputState state) {
		int headRow = getHeadGameCell().positionX;
		int headColumn = getHeadGameCell().positionY;
		
		GameBoardCell nextCell;
		if (state.Up()) {
			nextCell = gameBoard.getCell(headRow+1, headColumn);
		}
		if (state.Down()) {
			nextCell = gameBoard.getCell(headRow-1, headColumn);
		}
		if (state.Left()) {
			nextCell = gameBoard.getCell(headRow, headColumn-1);
		}
		if (state.Right()) {
			nextCell = gameBoard.getCell(headRow, headColumn+1);
		}
		
	}
	
	private boolean multipleInputStates(InputState) {
		if (state.Up()) {
			if (state.Down()
		}
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
