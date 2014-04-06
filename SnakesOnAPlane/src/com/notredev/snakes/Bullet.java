package com.notredev.snakes;

import java.util.LinkedList;

public class Bullet extends Actor {
	
	Direction direction;
	
	public Bullet(GameBoardCell startCell, Direction direction) {
		super(ActorType.BULLET, null);
		LinkedList<GameBoardCell> cells = new LinkedList<GameBoardCell>();
		cells.add(startCell);
		setActorCells(cells);
		
		direction = this.direction;
	}

	@Override
	public void update() {
		GameBoardCell nextCell = getActorCells().getFirst().getNextGameBoardCell(direction);
		if (nextCell != null) { // If the next cell is not out of bounds
			getActorCells().add(nextCell);
		}
		getActorCells().removeFirst();
	}
	
	
	
}
