package com.epam.esm.web.controller;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.util.UserHateoasAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * a class which performs GET operations on a resource called "user"
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserHateoasAssembler userAssembler;

    @Autowired
    public UserController(UserService userService,
                          UserHateoasAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo         - object witch contains information about pagination
     * @param userCriteriaInfo - object with information about user to search
     * @return a responseEntity with status code and collection of User, which represents a resource "user" from database
     * with links
     */
    @GetMapping
    public ResponseEntity<CollectionModel<UserDTO>> find(PageInfo pageInfo, UserCriteriaInfo userCriteriaInfo) {
        List<UserDTO> users = userService.find(pageInfo, userCriteriaInfo);
        return ResponseEntity.ok(userAssembler.toHateoasCollectionOfEntities(users));
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with name stored in a request path
     *
     * @param id an identification requested resource
     * @return a responseEntity with status code and object user, which represents a resource "user" from database
     * with links
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable("id") long id) {
        UserDTO userDTO = userService.findById(id);
        userAssembler.appendAsForMainEntity(userDTO);
        return ResponseEntity.ok(userDTO);
    }

}

