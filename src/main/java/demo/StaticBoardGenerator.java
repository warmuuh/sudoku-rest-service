package demo;

import org.springframework.stereotype.Component;

@Component
public class StaticBoardGenerator implements SudokuGenerator{

	private Integer[][] generateBoardFields() {
		Integer[][] fields = new Integer[9][];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Integer[9];
		}
		
		fields[2][0] = 9;
		fields[3][0] = 2;
		fields[4][0] = 3;
		

		fields[1][1] = 3;
		fields[5][1] = 4;
		fields[6][1] = 5;
		fields[7][1] = 1;

		fields[1][2] = 2;
		fields[8][2] = 9;

		fields[1][3] = 9;
		fields[8][3] = 5;
		

		fields[0][4] = 8;
		fields[8][4] = 6;
		
		fields[0][5] = 5;
		fields[7][5] = 9;

		fields[0][6] = 7;
		fields[7][6] = 3;
		

		fields[1][7] = 4;
		fields[2][7] = 8;
		fields[3][7] = 7;
		fields[7][7] = 5;
		

		fields[4][8] = 6;
		fields[5][8] = 9;
		fields[6][8] = 2;
		
		return fields;
	}



	@Override
	public Board generateBoard() {
		Board b = new Board();
		Integer[][] fields = generateBoardFields();
		b.setFields(fields);
		return b;
	}
	
}
