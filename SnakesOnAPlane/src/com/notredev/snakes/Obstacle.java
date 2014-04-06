package com.notredev.snakes;

public class Obstacle extends Actor {
	
	public Obstacle(java.util.LinkedList<GameBoardCell> actorCells) {
		super(ActorType.OBSTACLE, actorCells);
	}

	@Override
	public void update() {
		// Obstacles do not move or change on update
	}

}
