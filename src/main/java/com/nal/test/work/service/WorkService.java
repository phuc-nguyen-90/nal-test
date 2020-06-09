package com.nal.test.work.service;

import org.springframework.data.domain.Page;

import com.nal.test.work.exception.BadRequestException;
import com.nal.test.work.exception.EntityNotFoundException;
import com.nal.test.work.model.Work;

public interface WorkService {

	Work add(Work work) throws BadRequestException, EntityNotFoundException;
	
	Work update(Work work) throws EntityNotFoundException, BadRequestException;
	
	long delete(long id) throws EntityNotFoundException, BadRequestException;
	
	Page<Work> fetchAllWork(int page, int size, String sortBy) throws BadRequestException;
}
