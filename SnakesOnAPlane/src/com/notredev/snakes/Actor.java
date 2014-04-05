package com.notredev.snakes;

public class Actor {
	
	enum ActorType {SNAKE, OBSTACLE, BULLET}
	ActorType _type;
	java.util.LinkedList<GameBoardCell> actorCells = new java.util.LinkedList<GameBoardCell>(); 


	public Actor(ActorType type)
	{
		_type = type;
	}

	public ActorType getType()
	{
		return _type;
	}
	
	public java.util.LinkedList<GameBoardCell> getActorPositions() {
		return actorCells;
	}
	
}
