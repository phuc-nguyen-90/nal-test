package com.nal.test.work.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nal.test.work.model.Status;
import com.nal.test.work.model.Work;
import com.nal.test.work.service.WorkService;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private WorkService workService;

	@Test
	void testAddNewWorkSucessful() throws JsonProcessingException, Exception {
		Work work = initWork();
		mockMvc.perform(post("/work", initWork()).contentType("application/json")
				.content(objectMapper.writeValueAsString(work)))
				.andExpect(status().isOk());
	}
	
	private Work initWork() {
		Work work = new Work();
		work.setName("Work Test");
		work.setStartingDate(new Date());
		work.setEndingDate(new Date(System.currentTimeMillis() + 1000));
		work.setId(1L);
		Status status = new Status();
		status.setId(1L);
		status.setName("Planning");
		work.setStatus(status);
		return work;
	}
}
