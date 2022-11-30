package com.djk.komhunter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.djk.komhunter.models.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
	
}
