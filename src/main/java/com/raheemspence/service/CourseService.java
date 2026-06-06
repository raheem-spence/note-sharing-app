package com.raheemspence.service;

import com.raheemspence.dto.CreateCourseRequest;
import com.raheemspence.dto.CreateCourseResponse;
import com.raheemspence.dto.JoinCourseRequest;
import com.raheemspence.dto.JoinCourseResponse;
import com.raheemspence.model.Course;
import com.raheemspence.model.CourseMembership;
import com.raheemspence.model.User;
import com.raheemspence.repository.CourseMembershipRepository;
import com.raheemspence.repository.CourseRepository;
import com.raheemspence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseMembershipRepository courseMembershipRepository;

    public CourseService(UserRepository userRepository,
                         CourseRepository courseRepository,
                         CourseMembershipRepository courseMembershipRepository
    ) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseMembershipRepository = courseMembershipRepository;
    }

    public CreateCourseResponse createCourse(Long userId, CreateCourseRequest createCourseRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = new Course();

        course.setName(createCourseRequest.getCourseName());
        course.setSchool(createCourseRequest.getSchool());
        course.setJoinCode(createCourseRequest.getJoinCode());
        course.setCreator(user);

        Course savedCourse = courseRepository.save(course);

        CreateCourseResponse createCourseResponse = new CreateCourseResponse();

        createCourseResponse.setCourseName(savedCourse.getName());
        createCourseResponse.setSchool(savedCourse.getSchool());
        createCourseResponse.setCreatorUsername(user.getUsername());
        createCourseResponse.setCreator_id(user.getId());
        createCourseResponse.setJoinCode(createCourseRequest.getJoinCode());
        createCourseResponse.setCreatedAt(savedCourse.getCreatedAt());

        return createCourseResponse;
    }

    public JoinCourseResponse joinCourse(Long userId, JoinCourseRequest joinCourseRequest) {

        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found")
                );

        // Get course object, throw exception if not found
        Course course = courseRepository.findByJoinCode(joinCourseRequest.getJoinCode()).
                orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found")
                );

        // Check for duplicate membership join
        if (courseMembershipRepository.existsByUserIdAndCourseId(userId, course.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User already joined class"
            );
        }



        // Create a new course membership object
        CourseMembership courseMembership = new CourseMembership();

        // Fill in object fields with data from joinCourse request
        courseMembership.setCourse(course);
        courseMembership.setUser(user);


        // Save course membership object as a row in the db
        courseMembershipRepository.save(courseMembership);

        // Create response to send back to client and fill it with appropriate data
        JoinCourseResponse joinCourseResponse = new JoinCourseResponse();

        joinCourseResponse.setCourseName(course.getName());
        joinCourseResponse.setSchool(course.getSchool());
        joinCourseResponse.setCourseId(course.getId());

        return joinCourseResponse;

    }
}
