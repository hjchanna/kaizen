/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.app.security.user;

import com.mac.app.security.user.model.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
//@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @RequestMapping(path = "/user", method = RequestMethod.GET)
//    public ResponseEntity listUser() {
//        return new ResponseEntity(getUsers(), HttpStatus.OK);
//    }
//
//    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
//    public ResponseEntity listUser(@PathVariable(value = "id") String id) {
//        return new ResponseEntity(getUsers().stream().filter(user -> user.getIndexNo().equals(id)).findFirst().orElse(null), HttpStatus.OK);
//
//    }
//
//    @RequestMapping(path = "/user", method = RequestMethod.POST)
//    public ResponseEntity listUser(@RequestBody User user) {
//        return new ResponseEntity("18", HttpStatus.OK);
//    }
//
//    private List<User> getUsers() {
//        return userRepository.findAll();
//
//    }
    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public User getUser(@RequestBody User user) {
        User user1 = userRepository.findByNameAndPassword(user.getName(), user.getPassword());
        if (user1 != null) {
            System.out.println(user1.getName());
            System.out.println(user1.getPassword());
            System.out.println(user1.getRole());
            return user1;
        }
        return null;
    }

    @RequestMapping(path = "/save-user", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        user.setEpfNo(user.getPassword());
        return userRepository.save(user);
    }

    @RequestMapping(path = "/all-user", method = RequestMethod.GET)
    public List<User> users() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "/delete-user/{indexNo}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer indexNo) {
        userRepository.delete(indexNo);
    }

//    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
//    public HttpEntity optionRequest() {
//        return new HttpEntity(HttpEntity.EMPTY);
//    }

}
