package com.notredev.snakes;

public class PlayerSnake extends Snake {

	public PlayerSnake(GameBoardCell snakeHeadGameBoardCell, int playerNumber) {
		super(snakeHeadGameBoardCell, playerNumber);
	}

	@Override
	public void update() {
		InputState state = inputManager.GetInputForController(getPlayerNumber());
		move(getDirection(state));
	}

}
