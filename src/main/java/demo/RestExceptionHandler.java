package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler {

	
	@ExceptionHandler(SudokuValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> onValidationException(SudokuValidationException exception){
		Map<String, Object> result = new HashMap<>();
		result.put("errors", exception.getErrors());
		return result;
	}
	
	
	
}
