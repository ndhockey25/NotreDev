package com.notredev.snakes;

public class GameBoardCell 
{
	public enum Occupant{SNAKE, SNAKE_HEAD, RATTLE, FOOD, OBSTACLE, BULLETBILL, NOTHING};
	public java.util.List<Actor> listOfActors;
	int positionX;
	int positionY;
	boolean isAvailable;
	
	public GameBoardCell()
	{
		positionX = 0;
		positionY = 0;
		isAvailable = true;
		listOfActors = new java.util.ArrayList<Actor>();
	}

	public java.util.List<Actor> getListOfActors() {
		return listOfActors;
	}

	public void setListOfActors(java.util.List<Actor> listOfActors) {
		this.listOfActors = listOfActors;
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
		if (positionX != other.positionX)
			return false;
		if (positionY != other.positionY)
			return false;
		return true;
	}
	
	
	
}
