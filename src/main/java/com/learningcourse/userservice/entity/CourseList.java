package com.learningcourse.userservice.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseList {
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    private List<Course> courses;

    public CourseList() {
        courses = new ArrayList<>();
    }
}
