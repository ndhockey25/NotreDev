package com.notredev.snakes;

public class Obstacle extends Actor {
	
	public Obstacle() {
		super(ActorType.OBSTACLE);
	}

	@Override
	public void update() {
		// Obstacles do not move or change on update
	}

}
