package com.notredev.snakes;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class Actor {
	public enum ActorType {SNAKE, OBSTACLE, BULLET, FOOD}
	ActorType type;
	LinkedList<GameBoardCell> cells = new LinkedList<GameBoardCell>();
	GameBoard gameBoard = GameBoard.Instance();

	public Actor(ActorType type)
	{
		this.type = type;
	}

	public ActorType getType()
	{
		return type;
	}
	
	public Collection<GameBoardCell> getCells() {
		return new HashSet<GameBoardCell>(cells);
	}
	
	public void addCellFront(GameBoardCell cell) {
		cells.addFirst(cell);
		cell.addActor(this);
	}
	
	public void addCellBack(GameBoardCell cell) {
		cells.addLast(cell);
		cell.addActor(this);
	}
	
	public GameBoardCell removeCellFront() {
		GameBoardCell cell = cells.removeFirst();
		cell.removeActor(this);
		return cell;
	}
	
	public GameBoardCell removeCellBack() {
		GameBoardCell cell = cells.removeLast();
		cell.removeActor(this);
		return cell;
	}
	
	public abstract void update();
	
}
