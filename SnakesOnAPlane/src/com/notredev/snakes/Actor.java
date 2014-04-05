package com.notredev.snakes;

public class Actor {
	
	enum ActorType {SNAKE, OBSTACLE, BULLET}
	ActorType _type;
	
	public Actor(ActorType type)
	{
		_type = type;
	}

	public ActorType getType()
	{
		return _type;
	}
}
