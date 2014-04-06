package com.notredev.snakes;

public class CellOutOfBoundsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CellOutOfBoundsException(String error) {
		super(error);
	}
}
