package com.notredev.snakes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.notredev.snakes.Actor.ActorType;

import android.util.Log;

public class GameBoard {
	
	GameBoardCell[] gameBoardCells;

	int MAX_ROWS;
	int MAX_COLUMNS;
	
	private static GameBoard _gameBoard = new GameBoard();
	public java.util.List<Actor> listOfActors;


	public static GameBoard Instance()
	{
		return _gameBoard;
	}
	
	private GameBoard()
	{
		MAX_ROWS = 43;
		MAX_COLUMNS = 80;
		gameBoardCells = new GameBoardCell[MAX_ROWS*MAX_COLUMNS];
		for(int i=0; i<gameBoardCells.length; i++)
		{
			int posX = i % MAX_COLUMNS;
			int posY = i/MAX_COLUMNS;
			gameBoardCells[i] = new GameBoardCell(posX, posY);
		}
		//initialize list of actors for game
		listOfActors = new java.util.ArrayList<Actor>();
	}
	
	public GameBoardCell[] getGameBoardCells() {
		return gameBoardCells;
	}
	
	public int getMAX_ROWS() {
		return MAX_ROWS;
	}

	public void setMAX_ROWS(int mAX_ROWS) {
		MAX_ROWS = mAX_ROWS;
	}

	public int getMAX_COLUMNS() {
		return MAX_COLUMNS;
	}

	public void setMAX_COLUMNS(int mAX_COLUMNS) {
		MAX_COLUMNS = mAX_COLUMNS;
	}
	
	public java.util.List<Actor> getListOfActors() {
		return listOfActors;
	}

	//This method will loop through all gameboardcells, and
	//update the list of occupants
	public void updateOccupants()
	{
		//loop Actors, and populate cells that are occupied by actors
		java.util.List<GameBoardCell> listOfActorCells = new java.util.ArrayList<GameBoardCell>();
		java.util.List<GameBoardCell> listOfConflictCells = new java.util.ArrayList<GameBoardCell>();
		for(int i = 0; i<listOfActors.size();i++)
		{
			for(GameBoardCell cell : listOfActors.get(i).getCells())
			{
				listOfActorCells.add(cell);
			}
		}
		//loop Actor cells and resolve conflicts in those cells
		for(GameBoardCell cell : listOfActorCells)
		{
			resolveConflicts(cell);
		}
	}
	
	public GameBoardCell getCell(int row, int column) throws CellOutOfBoundsException {
		if (row > MAX_ROWS) {
			throw new CellOutOfBoundsException("Exceeded max row");
		}
		if (column > MAX_COLUMNS) {
			throw new CellOutOfBoundsException("Exceeded max column");
		}
		int index = MAX_COLUMNS*row + column;
		return gameBoardCells[index];
	}
	
	public void putCell(GameBoardCell cell) throws CellOutOfBoundsException {
		if (cell.getPositionX() > MAX_ROWS && cell.getPositionX() > MAX_COLUMNS) {
			throw new CellOutOfBoundsException("Cannot put cell in GameBoard. " + cell.getPositionX() + " exceeds GameBoard MAX_ROWS of " + MAX_ROWS + " and " + cell.getPositionY() + " exceeds GameBoard MAX_COLUMNS of " + MAX_COLUMNS);
		}
		else if (cell.getPositionX() > MAX_ROWS) {
			throw new CellOutOfBoundsException("Cannot put cell in GameBoard. " + cell.getPositionX() + " exceeds GameBoard MAX_ROWS of " + MAX_ROWS);
		}
		else if (cell.getPositionY() > MAX_COLUMNS) {
			throw new CellOutOfBoundsException("Cannot put cell in GameBoard. " + cell.getPositionY() + " exceeds GameBoard MAX_COLUMNS of " + MAX_COLUMNS);
		}
		
		gameBoardCells[(cell.getPositionY() * MAX_COLUMNS) + cell.getPositionX()] = cell;
	}
	
	public GameBoardCell getNextCell(GameBoardCell cell, Direction direction) throws CellOutOfBoundsException {
		int nextRow = cell.getPositionY();
		int nextColumn = cell.getPositionX();

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

		return getCell(nextRow, nextColumn);
	}
	
	private GameBoardCell resolveConflicts(GameBoardCell cell)
	{
		Map<ActorType, List<Actor>> actors = new HashMap<ActorType, List<Actor>>();
		for (Actor actor : cell.getActors()) {
			if (!actors.containsKey(actor.getType())) {
				actors.put(actor.getType(), new LinkedList<Actor>());
			}
			actors.get(actor.getType()).add(actor);
		}
		
		// Check for snake head and food overlap
		if (actors.containsKey(ActorType.FOOD) && actors.containsKey(ActorType.SNAKE)) {
			// For each snake on the food square, make him grow on his next move
			for (Actor snakeActor : actors.get(ActorType.SNAKE)) {
				Snake snake = (Snake)snakeActor;
				if (snake.getHeadCell().equals(cell)) {
					snake.setGrowOnNextMove(true);
				}
			}
			for (Actor foodActor : actors.get(ActorType.FOOD)) {
				cell.removeActor(foodActor);
			}
		}
		
		// Check for snake head and obstacle overlap
		if (actors.containsKey(ActorType.OBSTACLE) && actors.containsKey(ActorType.SNAKE)) {
			for (Actor snakeActor : actors.get(ActorType.SNAKE)) {
				Snake snake = (Snake)snakeActor;
				if(snake.getHeadCell().equals(cell)) { // A body part should never hit an obstacle
					snake.die();
				}	
			}
		}	
		
		// Check for multiple snake overlap
		if (actors.containsKey(ActorType.SNAKE)) {
			if (actors.get(ActorType.SNAKE).size() > 1) { // If there is more than one snake in the cell
				HashSet<Snake> snakeHeads = new HashSet<Snake>();
				for (Actor actor : cell.getActors()) {
					Snake snake = (Snake)actor;
					if (snake.getHeadCell().equals(cell)) {
						snakeHeads.add(snake);
					}
				}
				// Any snake whose head overlaps another snake should die
				for (Snake snake : snakeHeads) {
					snake.die();
				}
				
			}
		}
		
		// Check for bullet and food overlap
		if (actors.containsKey(ActorType.BULLET) && actors.containsKey(ActorType.FOOD)) {
			// There should only be one food item, but loop in case there is not
			for (Actor foodActor : cell.getActors()) {
				cell.removeActor(foodActor);
			}
		}
		
		
		// Bullets and snakes
		
		// Bullets and bullets
		
		return cell;
	}
}
