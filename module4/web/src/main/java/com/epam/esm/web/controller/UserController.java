package com.epam.esm.web.controller;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.util.UserHateoasAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * a class which performs GET operations on a resource called "user"
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserHateoasAssembler userAssembler;

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo         - object witch contains information about pagination
     * @param userCriteriaInfo - object with information about user to search
     * @return a responseEntity with status code and collection of User, which represents a resource "user" from database
     * with links
     */
    @GetMapping
    public ResponseEntity<CollectionModel<UserDTO>> find(@Valid PageInfo pageInfo, @Valid UserCriteriaInfo userCriteriaInfo) {
        List<UserDTO> users = userService.find(pageInfo, userCriteriaInfo);
        long count = userService.getCount();
        return ResponseEntity.ok(userAssembler.toHateoasCollectionOfEntities(users, pageInfo, count));
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with name stored in a request path
     *
     * @param id an identification requested resource
     * @return a responseEntity with status code and object user, which represents a resource "user" from database
     * with links
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable @Min(1) long id) {
        UserDTO userDTO = userService.findById(id);
        userAssembler.appendAsForMainEntity(userDTO);
        return ResponseEntity.ok(userDTO);
    }
}

