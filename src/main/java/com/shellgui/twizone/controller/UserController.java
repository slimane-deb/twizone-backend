package com.shellgui.twizone.controller;

import com.shellgui.twizone.dto.RegisterDTO;
import com.shellgui.twizone.exception.UserAlreadyExistsException;
import com.shellgui.twizone.model.User;
import com.shellgui.twizone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register",
            method = RequestMethod.POST
    )
    public int add(@RequestBody RegisterDTO registerDTO) throws UserAlreadyExistsException {
        try {
            return userService.add(registerDTO);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            throw new UserAlreadyExistsException();
        }
    }

    @RequestMapping(path = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAll() {
        return userService.getAll();
    }

    @RequestMapping(path = "/users/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getOne(@PathVariable("id") Integer id) {
        return userService.getOne(id);
    }

}
