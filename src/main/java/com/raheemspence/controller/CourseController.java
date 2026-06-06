package com.raheemspence.controller;

import com.raheemspence.dto.CreateCourseRequest;
import com.raheemspence.dto.CreateCourseResponse;
import com.raheemspence.service.CourseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public CreateCourseResponse createCourse(@Valid @RequestBody CreateCourseRequest createCourseRequest,
                                             HttpSession httpSession) {
        Long userId = (Long) httpSession.getAttribute("userId");

        return courseService.createCourse(userId, createCourseRequest);
    }
}
