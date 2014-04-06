package com.notredev.snakes;

import java.util.LinkedList;

public class GameBoardCell 
{
	GameBoard gameBoard = GameBoard.Instance();
	public enum Occupant{SNAKE, SNAKE_HEAD, RATTLE, FOOD, OBSTACLE, BULLETBILL, NOTHING};
	private LinkedList<Actor> actors = new LinkedList<Actor>();
	private int positionX;
	private int positionY;
	boolean isAvailable = true;
	
	public GameBoardCell(int posX, int posY)
	{
		positionX = posX;
		positionY = posY;
	}

	public LinkedList<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	public void removeActor(Actor actor) {
		actors.remove(actor);
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result
				+ ((actors == null) ? 0 : actors.hashCode());
		result = prime * result + positionX;
		result = prime * result + positionY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameBoardCell other = (GameBoardCell) obj;
		if (isAvailable != other.isAvailable)
			return false;
		if (actors == null) {
			if (other.actors != null)
				return false;
		} else if (!actors.equals(other.actors))
			return false;
		if (positionX != other.positionX)
			return false;
		if (positionY != other.positionY)
			return false;
		return true;
	}
	
}
