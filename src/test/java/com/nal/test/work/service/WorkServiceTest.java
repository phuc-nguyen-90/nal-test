package com.nal.test.work.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.nal.test.work.exception.BadRequestException;
import com.nal.test.work.exception.EntityNotFoundException;
import com.nal.test.work.model.Status;
import com.nal.test.work.model.Work;
import com.nal.test.work.repository.StatusRepository;
import com.nal.test.work.repository.WorkRepository;
import com.nal.test.work.service.impl.WorkServiceImpl;

@SpringBootTest
public class WorkServiceTest {

	@Mock
	private WorkRepository workRepository;

	@Mock
	private StatusRepository statusRepository;

	@InjectMocks
	private WorkServiceImpl workService = new WorkServiceImpl();

	@Test
	void testAddWork_whenWorkIsNull_thenThrowException() {
		Assert.assertThrows(BadRequestException.class, () -> workService.add(null));
	}

	@Test
	void testUpdateWork_whenWorkIsNull_thenThrowException() {
		Assert.assertThrows(BadRequestException.class, () -> workService.update(null));
	}

	@Test
	void testAddWork_whenIsNameEmpty_thenThrowException() {
		Work work = initWork();
		work.setName("");
		Assert.assertThrows(BadRequestException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenNameIsEmpty_thenThrowException() {
		Work work = initWork();
		work.setName("");
		Assert.assertThrows(BadRequestException.class, () -> workService.update(work));
	}

	@Test
	void testAddWork_whenStartingDateIsNull_thenThrowException() {
		Work work = initWork();
		work.setStartingDate(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenStartingDateIsNull_thenThrowException() {
		Work work = initWork();
		work.setStartingDate(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.update(work));
	}

	@Test
	void testAddWork_whenEndingDateIsNull_thenThrowException() {
		Work work = initWork();
		work.setEndingDate(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenEndingDateIsNull_thenThrowException() {
		Work work = initWork();
		work.setEndingDate(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.update(work));
	}

	@Test
	void testAddWork_whenStartingDateAfterEndingDate_thenThrowException() {
		Work work = initWork();
		work.setEndingDate(new Date(0));
		Assert.assertThrows(BadRequestException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenStartingDateAfterEndingDate_thenThrowException() {
		Work work = initWork();
		work.setEndingDate(new Date(0));
		Assert.assertThrows(BadRequestException.class, () -> workService.update(work));
	}

	@Test
	void testAddWork_whenStatusIsNull_ThenThrowException() {
		Work work = initWork();
		work.setStatus(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenStatusIsNull_thenThrowException() {
		Work work = initWork();
		work.setStatus(null);
		Assert.assertThrows(BadRequestException.class, () -> workService.update(work));
	}

	@Test
	void testAddWork_whenStatusIsNotFound_thenThrowException() {
		Work work = initWork();
		when(statusRepository.findById(work.getStatus().getId())).thenThrow(EntityNotFoundException.class);
		Assert.assertThrows(EntityNotFoundException.class, () -> workService.add(work));
	}

	@Test
	void testUpdateWork_whenStatusIsNotFound_thenThrowException() {
		Work work = initWork();
		when(statusRepository.findById(work.getStatus().getId())).thenThrow(EntityNotFoundException.class);
		Assert.assertThrows(EntityNotFoundException.class, () -> workService.update(work));
	}

	@Test
	void testAddWorkSuccessful() {
		Work work = initWork();
		when(statusRepository.findById(work.getStatus().getId())).thenReturn(Optional.of(work.getStatus()));
		when(workRepository.save(work)).thenReturn(work);
		Assert.assertEquals(work, workService.add(work));
	}

	@Test
	void testUpdateWorkSuccessful() {
		Work work = initWork();
		when(statusRepository.findById(work.getStatus().getId())).thenReturn(Optional.of(work.getStatus()));
		when(workRepository.save(work)).thenReturn(work);
		Assert.assertEquals(work, workService.add(work));
	}

	@Test
	void testDelete_whenWorkNotFound_thenThrowException() {
		when(workRepository.findById(1L)).thenReturn(Optional.empty());
		Assert.assertThrows(EntityNotFoundException.class, () -> workService.delete(1L));
	}

	@Test
	void testDeleteSuccessful() {
		when(workRepository.findById(1L)).thenReturn(Optional.of(initWork()));
		Assert.assertEquals(1L, workService.delete(1L));
	}

	@Test
	void testFetchWork_whenNegativePageNumber_thenThrowException() {
		Assert.assertThrows(BadRequestException.class, () -> workService.fetchAllWork(-1, 0, "name"));
	}

	@Test
	void testFetchWork_whenNegativeSize_thenThrowException() {
		Assert.assertThrows(BadRequestException.class, () -> workService.fetchAllWork(-1, 0, "name"));
	}

	@Test
	void testFetchWorkSuccessfull() {
		List<Work> works = new ArrayList<Work>();
		works.add(initWork());
		Page<Work> pageResult = new PageImpl<Work>(works);
		when(workRepository.findAll(PageRequest.of(0, 10, Sort.by("name")))).thenReturn(pageResult);
		Assert.assertEquals(1, workService.fetchAllWork(0, 10, "name").getContent().size());
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
