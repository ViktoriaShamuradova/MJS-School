package com.epam.esm.web.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.util.HateoasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * a class which performs GET operations on a resource called "user"
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public UserController(UserService userService, HateoasBuilder hateoasBuilder) {
        this.hateoasBuilder=hateoasBuilder;
        this.userService = userService;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @return a collection of User, which represents a resource "user" from data base
     */
    @GetMapping
    public RepresentationModel<?> findAll(@RequestParam Map<String, String> params) {
        List<UserDTO> users = userService.findAll(params);
        long userCount = userService.getCount();
        return hateoasBuilder.addLinksForListOfUsers(users, params, userCount);
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with name stored in a request path
     *
     * @param id an identification requested resource
     * @return an object which represents a target resource
     */
    @GetMapping("/{id}")
    public UserDTO find(@PathVariable("id") long id) {
        return userService.find(id);
    }
}

