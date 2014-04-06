package com.notredev.snakes;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.amazon.device.gamecontroller.GameController;
import com.amazon.device.gamecontroller.GameController.DeviceNotFoundException;
import com.amazon.device.gamecontroller.GameController.PlayerNumberNotFoundException;

public class InputManager {
	
	private static InputManager _inputManager = new InputManager();
	
	public InputState _remoteInput = new InputState(); 
	
	public static InputManager Instance()
	{
		return _inputManager;
	}
	
	private InputManager() {
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = false;
		try {
			handled = GameController.onKeyDown(keyCode, event);
		}
		catch (DeviceNotFoundException e) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					this._remoteInput.SetDown(true);
					break;
				case KeyEvent.KEYCODE_DPAD_UP:
					this._remoteInput.SetUp(true);
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					this._remoteInput.SetRight(true);
					break;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					this._remoteInput.SetLeft(true);
					break;
				case KeyEvent.KEYCODE_DPAD_CENTER:
					this._remoteInput.SetA(true);
					break;
			}
		}
		return handled;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean handled = false;
		try {
			handled = GameController.onKeyUp(keyCode, event);
		}
		catch (DeviceNotFoundException e) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					this._remoteInput.SetDown(false);
					break;
				case KeyEvent.KEYCODE_DPAD_UP:
					this._remoteInput.SetUp(false);
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					this._remoteInput.SetRight(false);
					break;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					this._remoteInput.SetLeft(false);
					break;
				case KeyEvent.KEYCODE_DPAD_CENTER:
					this._remoteInput.SetA(false);
					break;
			}
		}
		return handled;
	}
	
	public boolean onGenericMotionEvent(MotionEvent event) {
		boolean handled = false;
		try {
			handled = GameController.onGenericMotionEvent(event);
		}
		catch (DeviceNotFoundException e) {
		}
		return handled;
	}
	
	public InputState GetInputForController(int playerNumber) {
		if (playerNumber == 0) {
			return this._remoteInput;
		}
		else {
			GameController gameController = null;
			try {
				gameController = GameController.getControllerByPlayer(playerNumber - 1);
			}
			catch (PlayerNumberNotFoundException e) {
			}

			if (gameController != null) {
				InputState inputState = new InputState();
				
				float deltaX = gameController.getAxisValue(GameController.AXIS_STICK_LEFT_X);
				float deltaY = gameController.getAxisValue(GameController.AXIS_STICK_LEFT_Y);

				if ((deltaX * deltaX + deltaY * deltaY) > GameController.DEAD_ZONE * GameController.DEAD_ZONE) {
					if (deltaX < (-1) * GameController.DEAD_ZONE) {
						inputState.SetLeft(true);
					}
					else if (deltaX > GameController.DEAD_ZONE) {
						inputState.SetRight(true);
					}
					
					if (deltaY < (-1) * GameController.DEAD_ZONE) {
						inputState.SetUp(true);
					}
					else if (deltaY > GameController.DEAD_ZONE) {
						inputState.SetDown(true);
					}
				}
				
				// TODO: Fix DPAD
//				if (gameController.isButtonPressed(GameController.BUTTON_DPAD_DOWN)) {
//					inputState.SetDown(true);
//				}
//				
//				if (gameController.isButtonPressed(GameController.BUTTON_DPAD_UP)) {
//					inputState.SetUp(true);
//				}
//				
//				if (gameController.isButtonPressed(GameController.BUTTON_DPAD_LEFT)) {
//					inputState.SetLeft(true);
//				}
//				
//				if (gameController.isButtonPressed(GameController.BUTTON_DPAD_RIGHT)) {
//					inputState.SetRight(true);
//				}
				
				if (gameController.isButtonPressed(GameController.BUTTON_A)) {
					inputState.SetA(true);
				}
				
				return inputState;
			}
			return new InputState();
		}
	}

}

