package com.notredev.snakes;

public class PlayerSnake extends Snake {

	InputManager inputManager = InputManager.Instance();
	
	public PlayerSnake(GameBoardCell snakeHeadGameBoardCell, int playerNumber) {
		super(snakeHeadGameBoardCell, playerNumber);
	}

	@Override
	public void update() {
		InputState state = inputManager.GetInputForController(getPlayerNumber());
		try {
			move(getDirection(state));
		}
		catch (CellOutOfBoundsException e) {
			//TODO: Handle this
		}
	}

	private Direction getDirection(InputState state) {
		if (!(state.Up() ^ state.Down() ^ state.Left() ^ state.Right())) {
			// If multiple directions are pressed, continue in the current direction
			return currentDirection; 
		}
		if (state.Up()) {
			return Direction.UP;
		}
		if (state.Down()) {
			return Direction.DOWN;
		}
		if (state.Left()) {
			return Direction.LEFT;
		}
		if (state.Right()) {
			return Direction.RIGHT;
		}
		return currentDirection; // Default to the direction the snake is already moving
	}
	
}
