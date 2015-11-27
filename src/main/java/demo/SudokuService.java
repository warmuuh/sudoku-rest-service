package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SudokuService {

	@Autowired
	SudokuGenerator generator;
	
	@Autowired
	SudokuBoardValidator validator;
	
	public Board getNewBoard(){
		return generator.generateBoard();
	}

	public void validateBoard(Board b) throws SudokuValidationException {
		BoardState state = validator.isValid(b);
		b.setState(state);
	}
}
