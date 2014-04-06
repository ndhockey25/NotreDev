package com.notredev.snakes;

public class Bullet extends Actor {
	
	Direction direction;
	
	public Bullet(GameBoardCell startCell, Direction direction) {
		super(ActorType.BULLET);
		addCellBack(startCell);
		
		direction = this.direction;
	}

	@Override
	public void update() {
		GameBoardCell nextCell = gameBoard.getNextCell(cells.getFirst(), direction);
		if (nextCell != null) { // If the next cell is not out of bounds
			addCellFront(nextCell);
		}
		removeCellBack();
	}
	
	
	
}
