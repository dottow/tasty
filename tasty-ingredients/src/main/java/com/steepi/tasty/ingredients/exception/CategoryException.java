package com.steepi.tasty.ingredients.exception;

public class CategoryException extends Exception {
	private static final long serialVersionUID = -2873653366020566177L;

	public CategoryException() {
		super();
	}

	public CategoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryException(String message) {
		super(message);
	}

	public CategoryException(Throwable cause) {
		super(cause);
	}
}
