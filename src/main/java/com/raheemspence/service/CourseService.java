package com.raheemspence.service;

import com.raheemspence.dto.CreateCourseRequest;
import com.raheemspence.dto.CreateCourseResponse;
import com.raheemspence.model.Course;
import com.raheemspence.model.User;
import com.raheemspence.repository.CourseRepository;
import com.raheemspence.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public CourseService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public CreateCourseResponse createCourse(Long userId, CreateCourseRequest createCourseRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = new Course();

        course.setName(createCourseRequest.getClassName());
        course.setSchool(createCourseRequest.getSchool());
        course.setJoinCode(createCourseRequest.getJoinCode());
        course.setCreator(user);

        Course savedClass = courseRepository.save(course);

        CreateCourseResponse createCourseResponse = new CreateCourseResponse();

        createCourseResponse.setClassName(savedClass.getName());
        createCourseResponse.setSchool(savedClass.getSchool());
        createCourseResponse.setCreatorUsername(user.getUsername());
        createCourseResponse.setCreator_id(user.getId());
        createCourseResponse.setJoinCode(createCourseRequest.getJoinCode());
        createCourseResponse.setCreatedAt(savedClass.getCreatedAt());

        return createCourseResponse;
    }

}
