package com.notredev.snakes;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class Snake extends Actor {

	//Direction direction;
	
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
	
	//public void move(Map<Direction, Boolean> input) {
		
		
	//}
	
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
