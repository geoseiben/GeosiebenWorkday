package com.geosieben.gsbworkday.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.geosieben.gsbworkday.entity.Course;
import com.geosieben.gsbworkday.repository.CourseRepository;



@Controller
@RequestMapping("/course")
public class CourseController {

private CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
}

    @GetMapping("/manageCourse")
    public String navCourses() {
        return "courses";
    }
   
    @GetMapping("/viewCourse")
    public String navViewCourse(Model model,@RequestParam("courseId") int courseId) {
        System.out.println("CourseId"+courseId);
        Course course=courseRepository.findById(courseId).orElse(null);
        model.addAttribute("course",course);
        return "viewCourse";
    }

    @GetMapping("/learn")
    public String navMyCourse() {
        return "myCourses";
    }
    @GetMapping("/completeCourse")
    public String navCourseCompletion(Model model,@RequestParam("courseId") int courseId) {
          Course course=courseRepository.findById(courseId).orElse(null);
        model.addAttribute("course",course);
        return "completeCourse";
    }
    @GetMapping("/viewAssesment")
    public String navViewAssesments() {
        return "viewAssesment";
    }
      

}
