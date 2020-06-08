package com.nal.test.work.service;

import org.springframework.data.domain.Page;

import com.nal.test.work.model.Work;

public interface WorkService {

	Work add(Work work);
	
	Work update(Work work);
	
	long delete(long id);
	
	Page<Work> fetchAllWork();
}
