package demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

	@Autowired
	SudokuService service;

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public Board getBoard() {
		return service.getNewBoard();
	}

	@RequestMapping(value = "/board", method = RequestMethod.PUT)
	public ResponseEntity<Board> validateBoard(@RequestBody @Valid Board b,
			BindingResult validationErrors) throws Exception {
		if (validationErrors.hasErrors())
			return new ResponseEntity<Board>(HttpStatus.BAD_REQUEST);

		service.validateBoard(b);
		return new ResponseEntity<Board>(b, HttpStatus.OK);
	}
}
