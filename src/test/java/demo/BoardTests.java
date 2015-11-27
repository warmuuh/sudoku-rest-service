package demo;


import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BoardTests {

	@Autowired
	WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before 
	public void setup(){
		mockMvc = webAppContextSetup(wac).build();
	}
		
	@Test
	public void shouldReturnOkOnBoard() throws Exception {
		mockMvc.perform(get("/board"))
			.andExpect(status().isOk());
	}

	@Test
	public void shouldErrorOnEmptyBoardFields() throws Exception {
		Board b = new Board();
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void shouldErrorOnInvalidInput() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		fields[0][0] = 10;

		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldErrorOnInvalidLetters() throws Exception {

		String fieldDescription = "fields:[['a'],[],[],[],[],[],[],[],[]]";
		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(fieldDescription))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldErrorOnInvalidBoardRow() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		fields[0][0] = 1;
		fields[0][1] = 1;

		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest());
	}
	

	@Test
	public void shouldErrorOnInvalidBoardColumn() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		fields[0][0] = 1;
		fields[1][0] = 1;

		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldErrorOnInvalidBoardCell() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		fields[0][0] = 1;
		fields[1][1] = 1;

		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void shouldDescribeErrorOnInvalidBoard() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		fields[0][0] = 1;
		fields[1][1] = 1;

		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors[0].x", is(0)))
			.andExpect(jsonPath("$.errors[0].y", is(0)))
			.andExpect(jsonPath("$.errors[1].x", is(1)))
			.andExpect(jsonPath("$.errors[1].y", is(1)));
	}
	
	
	@Test
	public void shouldSuccessOnValidBoard() throws Exception {
		Board b = new Board();
		Integer[][] fields = createTestBoard();
		b.setFields(fields);
		
		mockMvc.perform(put("/board")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(b)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.state", is("VALID")));
	}
	
	
	
	
	private String toJson(Board b) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(b);
	}
	
	public Integer[][] createTestBoard(){
		Integer[][] fields = new Integer[9][];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Integer[9];
		}
		return fields;
	}
}
