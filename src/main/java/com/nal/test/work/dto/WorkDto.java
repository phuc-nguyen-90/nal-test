package com.nal.test.work.dto;

import java.util.Date;

import lombok.Data;

@Data
public class WorkDto {

	private long id;
	private String name;
	private StatusDto status;
	private Date startingDate;
	private Date endingDate;

}
