package com.learningcourse.userservice.service;

import com.learningcourse.userservice.entity.Course;
import com.learningcourse.userservice.entity.CourseList;
import com.learningcourse.userservice.entity.User;
import com.learningcourse.userservice.repository.UserRepository;
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonString;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/register")
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public List<Course> viewAllCourses(){
        List<Course> availableCourses = new ArrayList<Course>();
        try {
            Course[] courses = restTemplate.getForObject("http://localhost:9012/courses/getall", Course[].class);
            if(courses!=null){
                availableCourses = Arrays.asList(courses);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return availableCourses;
    }

    public List<Course> viewAllCoursesByTechnologyOrName() {
        return new ArrayList<>();
    }

    public User getUser(User user) {
        User authenticatedUser = null;
        ArrayList<User>filteredList = new ArrayList<User>();
        try {
            User loginUser = new User();
            if(user!=null) {
                if(user.getUsername()!=null) {
                    loginUser.setUsername(user.getUsername());
                }
                if(user.getPassword()!=null) {
                    loginUser.setPassword(user.getPassword());
                }
            }
            Example<User> example = Example.of(loginUser);
          filteredList = (ArrayList<User>) userRepository.findAll(example);
          authenticatedUser = filteredList.size() > 0 ? filteredList.get(0):null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return authenticatedUser;
    }
}
