package demo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SudokuBoardValidator {

	public BoardState isValid(Board b) throws SudokuValidationException {

		List<SudokuError> errors = new LinkedList<SudokuError>();

		validateDigits(b, errors);
		validateRows(b, errors);
		validateColumns(b, errors);
		validateCells(b, errors);

		if (errors.size() > 0) {
			throw new SudokuValidationException(errors);
		}

		BoardState state = getBoardState(b);
		return state;
	}

	private BoardState getBoardState(Board b) {
		for (int i = 0; i < b.getFields().length; i++) {
			for (int j = 0; j < b.getFields().length; j++) {
				if (b.getFields()[i][j] == null) {
					return BoardState.VALID;
				};
			}
		}
		return BoardState.COMPLETED;
	}

	private void validateDigits(Board b, List<SudokuError> errors)
			throws SudokuValidationException {
		int length = b.getFields().length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				Integer digit = b.getFields()[i][j];
				if (digit != null) {
					if (digit < 1 || digit > 9)
						errors.add(new SudokuError(i, j));
				}
			}
		}
	}

	private boolean validateColumns(Board b, List<SudokuError> errors) {
		int length = b.getFields().length;

		for (int i = 0; i < length; i++) {
			Integer[] column = new Integer[length];
			for (int j = 0; j < length; j++) {
				column[j] = b.getFields()[j][i];
			}
			Collection<Integer> invalidIds = validatePartial(column);
			for (Integer invalidId : invalidIds) {
				errors.add(new SudokuError(invalidId, i));
			}
		}
		return true;
	}

	private boolean validateCells(Board b, List<SudokuError> errors) {
		int cellCount = 3;
		for (int i = 0; i < cellCount; i++) {
			for (int j = 0; j < cellCount; j++) {
				if (!validateCell(i, j, b, errors)) {
					return false;
				}
			}

		}
		return true;
	}

	private boolean validateCell(int cellCoordX, int cellCoordY, Board b,
			List<SudokuError> errors) {
		int length = 3;
		Integer[] digits = new Integer[b.getFields().length];
		int curDigit = 0;
		int cellX = cellCoordX * 3;
		int cellY = cellCoordY * 3;
		for (int i = cellX; i < length; i++) {
			for (int j = cellY; j < length; j++) {
				digits[curDigit++] = b.getFields()[i][j];
			}
		}
		Collection<Integer> invalidIds = validatePartial(digits);
		for (Integer invalidId : invalidIds) {
			int invalidIdRow = invalidId / 3;
			int invalidIdCol = invalidId % 3;
			errors.add(new SudokuError(cellX + invalidIdRow, cellY
					+ invalidIdCol));
		}
		return true;
	}

	private void validateRows(Board b, List<SudokuError> errors) {
		for (int i = 0; i < b.getFields().length; i++) {
			Collection<Integer> invalidIds = validatePartial(b.getFields()[i]);
			for (Integer invalidId : invalidIds) {
				errors.add(new SudokuError(i, invalidId));
			}
		}
	}

	private Collection<Integer> validatePartial(Integer[] integers) {
		Set<Integer> invalidIds = new HashSet();
		for (int i = 0; i < integers.length - 1; i++) {
			for (int j = i + 1; j < integers.length; j++) {
				if (Objects.equals(integers[i], integers[j])) {
					if (integers[i] != null && integers[j] != null) {
						invalidIds.add(i);
						invalidIds.add(j);
					}
				}
			}
		}

		return invalidIds;
	}

}
