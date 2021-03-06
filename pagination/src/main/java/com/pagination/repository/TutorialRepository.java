package com.pagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pagination.entities.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long>{

	Page<Tutorial> findAll(Pageable pageable);
	Page<Tutorial> findByPublished(Boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContainingIgnoreCase(String title, Pageable pageable);
	
	@Query(value = "SELECT * FROM tutorial WHERE unaccent(title) ilike unaccent('%title%')",
			nativeQuery = true)
	Page<Tutorial> findTitleCustom(String title, Pageable pageable);
}
