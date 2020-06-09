package com.nal.test.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nal.test.work.model.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long>{

}
