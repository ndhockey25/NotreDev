package com.notredev.snakes;

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
		MAX_ROWS = 68;
		MAX_COLUMNS = 120;
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
			for(GameBoardCell cell : listOfActors.get(i).getActorCells())
			{
				listOfActorCells.add(cell);
			}
		}
		//loop Actor cells and resolve conflicts in those cells
		for(GameBoardCell cell : listOfActorCells)
		{
			if(cell.listOfActors.size() > 1)
			{
				listOfConflictCells.add(resolveConflicts(cell));
			}
		}
		//create a set of new cells that resulted from the conflicts, and add them afterwards
		for(GameBoardCell cell : listOfConflictCells)
		{
			GameBoard.Instance().putCell(cell);
		}
		//after this, we will draw
	}
	
	public GameBoardCell getCell(int row, int column) throws CellOutOfBoundsException {
		if (row > MAX_ROWS) {
			throw new CellOutOfBoundsException("Exceeded max row");
		}
		if (column > MAX_COLUMNS) {
			throw new RuntimeException("Exceeded max column");
		}
		int index = MAX_COLUMNS*row + column;
		return gameBoardCells[index];
	}
	
	public void putCell(GameBoardCell cell)
	{
		gameBoardCells[(cell.positionY * MAX_COLUMNS) + cell.positionX] = cell;
	}
	
	private GameBoardCell resolveConflicts(GameBoardCell cell)
	{
		//check for 
		//snake head & snake body
		//snake head and obstacle
		//snake head & food
		for(Actor actor : listOfActors)
		{
			
		}
		
		return cell;
	}
}
