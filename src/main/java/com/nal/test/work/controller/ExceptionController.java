package com.nal.test.work.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nal.test.work.exception.BadRequestException;
import com.nal.test.work.exception.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Object> exception(BadRequestException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<Object> exception(EntityNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
