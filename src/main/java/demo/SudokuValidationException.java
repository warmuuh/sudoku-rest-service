package demo;

import java.util.LinkedList;
import java.util.List;

public class SudokuValidationException extends Exception {

	List<SudokuError> errors;
	
	public SudokuValidationException(List<SudokuError> errors) {
		super();
		this.errors = errors;
	}
	
	public List<SudokuError> getErrors() {
		return errors;
	}
}
