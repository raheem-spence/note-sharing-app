package com.raheemspence.repository;

import com.raheemspence.model.CourseMembership;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseMembershipRepository extends JpaRepository<CourseMembership, Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
