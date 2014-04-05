package com.notredev.snakes;

import java.util.LinkedList;

public abstract class Actor {
	
	enum ActorType {SNAKE, OBSTACLE, BULLET}
	ActorType _type;
	LinkedList<GameBoardCell> _actorCells = new LinkedList<GameBoardCell>(); 


	public Actor(ActorType type, java.util.LinkedList<GameBoardCell> actorCells)
	{
		_type = type;
		_actorCells = actorCells;
	}

	public ActorType getType()
	{
		return _type;
	}
	
	public LinkedList<GameBoardCell> getActorCells() {
		return _actorCells;
	}
	
	public void setActorCells(LinkedList<GameBoardCell> actorCells) {
		_actorCells = actorCells;
	}
	
}
