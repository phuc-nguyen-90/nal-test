package com.nal.test.work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nal.test.work.model.Work;
import com.nal.test.work.service.WorkService;

@RestController
@RequestMapping("/work")
public class WorkController {

	@Autowired
	private WorkService workService;

	@GetMapping
	public ResponseEntity<Page<Work>> getAllWorks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		Page<Work> workPages = workService.fetchAllWork(page, size, sortBy);

		return new ResponseEntity<Page<Work>>(workPages, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Work> addNewWork(@RequestBody Work work) {
		Work insertedWork = workService.add(work);

		return new ResponseEntity<Work>(insertedWork, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Work> updateWork(@PathVariable("id") long id, @RequestBody Work work) {
		Work updatedWork = workService.update(work);

		return new ResponseEntity<Work>(updatedWork, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") long id) {
		workService.delete(id);
		return HttpStatus.OK;
	}
}
