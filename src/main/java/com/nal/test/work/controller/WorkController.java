package com.nal.test.work.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nal.test.work.dto.StatusDto;
import com.nal.test.work.dto.WorkDto;
import com.nal.test.work.model.Status;
import com.nal.test.work.model.Work;

@RestController
@RequestMapping("/work")
public class WorkController {

	@Autowired
    private ModelMapper modelMapper;
	
	private WorkDto convertToWorkDto(final Work work) {
		return modelMapper.map(work, WorkDto.class);
	}
	
	private StatusDto convertToStatusDto(final Status status) {
		return modelMapper.map(status, StatusDto.class);
	}
	
	private Work convertToWorkModel(final WorkDto workDto) {
		Work work = modelMapper.map(workDto, Work.class);
		
		return work;
	}
}
