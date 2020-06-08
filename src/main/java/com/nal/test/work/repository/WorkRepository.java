package com.nal.test.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nal.test.work.model.Work;

public interface WorkRepository extends JpaRepository<Work, Long>{

}
