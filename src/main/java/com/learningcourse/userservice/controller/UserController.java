package com.learningcourse.userservice.controller;

import com.learningcourse.userservice.entity.Course;
import com.learningcourse.userservice.entity.User;
import com.learningcourse.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.slf4j.SLF4JLogger;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    //@Autowired
    //private SLF4JLogger logger;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user){
       // logger.info("Inside saveUser UserController");
        User userRes = userService.saveUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<User>(userRes,responseHeaders, HttpStatus.OK );
    }

    @RequestMapping(value = "/getall",method = RequestMethod.GET)
    public List<Course> viewAllCourses(){
        return  userService.viewAllCourses();
    }

    @RequestMapping(value = "/exists/{username}",method = RequestMethod.GET)
    public boolean isUserExists(@PathVariable("username") String username){
        boolean isExists = false;
        User newUser =new User();
        newUser.setUsername(username);
        User user  = userService.getUser(newUser);
        if(user!=null){
            isExists = true;
        }
        return isExists;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public HashMap<String,Object> login(@RequestBody User user){
        ResponseEntity<HashMap<String,Object>> responseEntity = null;
        HashMap<String,Object> data = new HashMap<>();
        User userRes =userService.getUser(user);
       // HttpHeaders responseHeaders = new HttpHeaders();
        if(userRes!=null) {
            data.put("user",userRes);
            data.put("status",HttpStatus.OK);
           // responseEntity = new ResponseEntity<HashMap<String,Object>>(data, responseHeaders, HttpStatus.OK);
        }else{
            data.put("status",HttpStatus.UNAUTHORIZED);
            data.put("errorMsg","Incorrect login , please try again.");
            //responseEntity = new ResponseEntity<HashMap<String,Object>>(data, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
        return data;
    }
}
