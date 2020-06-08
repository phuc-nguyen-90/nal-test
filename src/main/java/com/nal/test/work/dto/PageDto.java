package com.nal.test.work.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.NonNull;

@Data
public class PageDto<T> {

	private List<?> data;
	
	private boolean hasNext;
	
	private boolean hasPrevious;
	
	public PageDto(@NonNull Page<?> pageData) {
		this.data = pageData.getContent();
		this.hasNext = pageData.hasNext();
		this.hasPrevious = pageData.hasPrevious();
	}
}
