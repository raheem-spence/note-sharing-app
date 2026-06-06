package com.raheemspence.repository;

import com.raheemspence.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByJoinCode(String joinCode);
}
