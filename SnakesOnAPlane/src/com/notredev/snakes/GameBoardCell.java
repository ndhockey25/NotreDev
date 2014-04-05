package com.notredev.snakes;

public class GameBoardCell 
{
	public enum Occupant{SNAKE, SNAKE_HEAD, RATTLE, FOOD, OBSTACLE, BULLETBILL, NOTHING};
	public java.util.List<Occupant> listOfOccupants;
	int positionX;
	int positionY;
	boolean isAvailable;
	
	public GameBoardCell()
	{
		positionX = 0;
		positionY = 0;
		isAvailable = true;
		listOfOccupants = new java.util.ArrayList<Occupant>();
	}

	public java.util.List<Occupant> getListOfOccupants() {
		return listOfOccupants;
	}

	public void setListOfOccupants(java.util.List<Occupant> listOfOccupants) {
		this.listOfOccupants = listOfOccupants;
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
