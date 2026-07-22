package com.raheemspence.service;

import com.raheemspence.dto.request.CreateCourseRequest;
import com.raheemspence.dto.request.JoinCourseRequest;
import com.raheemspence.dto.response.CourseResponse;
import com.raheemspence.dto.response.CreateCourseResponse;
import com.raheemspence.dto.response.JoinCourseResponse;
import com.raheemspence.model.Course;
import com.raheemspence.model.CourseMembership;
import com.raheemspence.model.User;
import com.raheemspence.repository.CourseMembershipRepository;
import com.raheemspence.repository.CourseRepository;
import com.raheemspence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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

    public List<CourseResponse> getCoursesByUserId(Long userId) {

        // Get list of course memberships
        List<CourseMembership> courseMemberships = courseMembershipRepository.findByUserId(userId);

        // Create new list to store CourseResponse dto's
        List<CourseResponse> courseResponseList = new ArrayList<>();

        // Loop through list of notes and retrieve fields to populate CourseResponse dto's
        for (CourseMembership courseMembership: courseMemberships) {
            Course course = courseMembership.getCourse();

            Long id = course.getId();
            String name = course.getName();

            CourseResponse courseResponse = new CourseResponse();

            // Populate dto
            courseResponse.setId(id);
            courseResponse.setName(name);

            // add dto to list
            courseResponseList.add(courseResponse);
        }

        return courseResponseList;
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
        createCourseResponse.setCreatorFirstName(user.getFirstName());
        createCourseResponse.setCreatorLastName(user.getLastName());
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
