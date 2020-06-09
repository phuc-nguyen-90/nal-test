package com.nal.test.work.service.impl;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nal.test.work.exception.BadRequestException;
import com.nal.test.work.exception.EntityNotFoundException;
import com.nal.test.work.model.Work;
import com.nal.test.work.repository.StatusRepository;
import com.nal.test.work.repository.WorkRepository;
import com.nal.test.work.service.WorkService;

@Service
public class WorkServiceImpl implements WorkService {
	
	@Autowired
	private WorkRepository workRepository;
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public Work add(Work work) throws BadRequestException, EntityNotFoundException {
		validateWork(work);
		Date now = new Date();
		work.setCreatedDate(now);
		work.setUpdatedDate(now);
		return workRepository.save(work);
	}

	@Override
	public Work update(Work work) throws EntityNotFoundException, BadRequestException {
		validateWork(work);
		Optional<Work> optionalWork = workRepository.findById(work.getId());
		
		if (optionalWork.isPresent()) {
			work.setUpdatedDate(new Date());
			work.setCreatedDate(optionalWork.get().getCreatedDate());
			return workRepository.save(work);
		}
		throw new EntityNotFoundException("Not Found Work");
	}

	@Override
	public long delete(long id) throws EntityNotFoundException {
		Optional<Work> optionalWork = workRepository.findById(id);
		if (optionalWork.isPresent()) {
			workRepository.deleteById(id);
		}
		throw new EntityNotFoundException("Not Found Work");
	}

	@Override
	public Page<Work> fetchAllWork(int page, int size, String sortBy) throws BadRequestException {
		if (page < 0) {
			throw new BadRequestException("Invalid page number");
		}
		if (size <= 0) {
			throw new BadRequestException("Invalid page size");
		}
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return workRepository.findAll(pageable);
	}

	private void validateWork(Work work) throws BadRequestException, EntityNotFoundException {
		if (Objects.isNull(work)) {
			throw new BadRequestException("Work is null");
		}
		if (Objects.isNull(work.getStartingDate())) {
			throw new BadRequestException("Missing starting date");
		}
		if (Objects.isNull(work.getEndingDate())) {
			throw new BadRequestException("Missing ending date");
		}
		if (work.getEndingDate().before(work.getStartingDate())) {
			throw new BadRequestException("Ending date is less than starting date");
		}
		if (StringUtils.isEmpty(work.getName())) {
			throw new BadRequestException("Work name is empty");
		}
		if (Objects.isNull(work.getStatus())) {
			throw new BadRequestException("Missing work status");
		}
		if (!statusRepository.findById((long) work.getStatus().getId()).isPresent()) {
			throw new EntityNotFoundException("Not found work status");
		}
	}
}
