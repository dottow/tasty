package com.steepi.tasty.ingredients.exception;

public class IngredientException extends Exception {
	private static final long serialVersionUID = 9160711177910344120L;

	public IngredientException() {
		super();
	}

	public IngredientException(String message, Throwable cause) {
		super(message, cause);
	}

	public IngredientException(String message) {
		super(message);
	}

	public IngredientException(Throwable cause) {
		super(cause);
	}
}
