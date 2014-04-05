package com.notredev.snakes;

public class Actor {
	
	enum ActorType {SNAKE, OBSTACLE, BULLET}
	ActorType _type;
	java.util.LinkedList<GameBoardCell> _actorCells = new java.util.LinkedList<GameBoardCell>(); 


	public Actor(ActorType type, java.util.LinkedList<GameBoardCell> actorCells)
	{
		_type = type;
		_actorCells = actorCells;
	}

	public ActorType getType()
	{
		return _type;
	}
	
	public java.util.LinkedList<GameBoardCell> getActorCells() {
		return _actorCells;
	}
	
}
