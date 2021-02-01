package com.epam.esm.web;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * a class which performs REST's CRUD operations on a resource called "Tag"
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @return a collection of Tag, which represents a resource "tags" from data base
     */
    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param tag an object which represents a resource "tags" that must be created
     *            in the data source
     * @return an object which represents Http response of CREATE operation,
     * which body contains an information about successful creature
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Tag tag) {
        tagService.create(tag);
        return new ResponseEntity<>("The tag=" + tag.getName() + " was created", HttpStatus.OK);
    }

    /**
     * a method which realizes REST's DELETE operation of a specific resource with ID stored in a request path
     *
     * @param id an identification number of a resource which should be deleted
     * @return an object which represents Http response of DELETE operation
     */
    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        tagService.delete(id);
        return new ResponseEntity<>("Tag with id= " + id + " was deleted", HttpStatus.OK);
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with name stored in a request path
     *
     * @param name an identification requested resource
     * @return an object which represents a target resource
     */
    @GetMapping("/{name}")
    public Tag find(@PathVariable("name") String name) {
        return tagService.find(name);
    }
}
