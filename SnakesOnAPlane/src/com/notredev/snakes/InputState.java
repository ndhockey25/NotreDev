package com.notredev.snakes;

public class InputState {
	private boolean isUp = false;
	private boolean isDown = false;
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isA = false;

	public InputState() {
	}

	public InputState(boolean up, boolean down, boolean left, boolean right,
			boolean a) {
		this.isUp = up;
		this.isDown = down;
		this.isLeft = left;
		this.isRight = right;
		this.isA = a;
	}

	public boolean Up() {
		return this.isUp;
	}

	public boolean Down() {
		return this.isDown;
	}

	public boolean Left() {
		return this.isLeft;
	}

	public boolean Right() {
		return this.isRight;
	}

	public boolean A() {
		return this.isA;
	}

	public void SetUp(boolean state) {
		this.isUp = state;
	}

	public void SetDown(boolean state) {
		this.isDown = state;
	}

	public void SetLeft(boolean state) {
		this.isLeft = state;
	}

	public void SetRight(boolean state) {
		this.isRight = state;
	}

	public void SetA(boolean state) {
		this.isA = state;
	}
}
