package com.notredev.snakes;

public class Food extends Actor {
	
	public Food(GameBoardCell startCell) {
		super(ActorType.FOOD);
		addCellBack(startCell);
	}

	@Override
	public void update() {
	}
}
