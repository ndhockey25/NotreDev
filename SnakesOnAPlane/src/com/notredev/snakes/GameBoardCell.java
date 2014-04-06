package com.notredev.snakes;

import android.util.Log;

public class GameBoardCell 
{
	GameBoard gameBoard = GameBoard.Instance();
	public enum Occupant{SNAKE, SNAKE_HEAD, RATTLE, FOOD, OBSTACLE, BULLETBILL, NOTHING};
	public java.util.List<Actor> listOfActors;
	int positionX;
	int positionY;
	boolean isAvailable;
	
	public GameBoardCell(int posX, int posY)
	{
		positionX = posX;
		positionY = posY;
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
	
	protected GameBoardCell getNextGameBoardCell(Direction direction) {
		int nextRow = positionX;
		int nextColumn = positionY;

		switch (direction) {
        	case UP:
        		nextRow--;
        		break;
        	case DOWN:
        		nextRow++;
        		break;
        	case LEFT:
        		nextColumn--;
        		break;
        	case RIGHT:
        		nextColumn++;
        		break;
		}

		
		GameBoardCell nextCell = null;
		try {
			nextCell = gameBoard.getCell(nextRow, nextColumn);
		}
		catch (CellOutOfBoundsException e) {
			Log.e("getCell", e.toString());
		}
		
		return nextCell;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result
				+ ((listOfActors == null) ? 0 : listOfActors.hashCode());
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
		if (listOfActors == null) {
			if (other.listOfActors != null)
				return false;
		} else if (!listOfActors.equals(other.listOfActors))
			return false;
		if (positionX != other.positionX)
			return false;
		if (positionY != other.positionY)
			return false;
		return true;
	}
	
}
