package demo;

import javax.validation.constraints.NotNull;

public class Board {

	@NotNull
	private Integer[][] fields;
	
	private BoardState state = BoardState.VALID;
	
	public Integer[][] getFields() {
		return fields;
	}
	
	public void setFields(Integer[][] fields) {
		this.fields = fields;
	}
	
	public void setState(BoardState state) {
		this.state = state;
	}
	
	public BoardState getState() {
		return state;
	}

}
