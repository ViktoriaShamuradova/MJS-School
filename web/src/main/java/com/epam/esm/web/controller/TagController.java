package com.epam.esm.web.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.PageInfo;
import com.epam.esm.service.TagService;
import com.epam.esm.util.HateoasBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * a class which performs REST's CRUD operations on a resource called "Tag"
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;
    private final HateoasBuilder hateoasBuilder;

    @Autowired
    public TagController(TagService tagService, HateoasBuilder hateoasBuilder) {
        this.hateoasBuilder = hateoasBuilder;
        this.tagService = tagService;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo - map witch contains information about pagination
     * @return a collection of TagDTO with links, which represents a resource "tag" from data base
     */
    @GetMapping
    public RepresentationModel<?> findAll(@RequestParam PageInfo pageInfo) {
        List<TagDTO> tags = tagService.findAll(pageInfo);
        long tagCount = tagService.getCount();
        return hateoasBuilder.addLinksForListOfTag(tags, pageInfo, tagCount);
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
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tag) {
        TagDTO tagNew = tagService.create(tag);
        return new ResponseEntity<>(hateoasBuilder.addLinksForTag(tagNew), HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tag, UriComponentsBuilder builder) {
//
//        UriComponents uriComponents =
//                builder.path("/{name}").buildAndExpand(tag.getName());
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setLocation(uriComponents.toUri());
//        return new ResponseEntity<>(tagService.create(tag), responseHeaders, HttpStatus.CREATED);
//    }

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
    public TagDTO find(@PathVariable("name") String name) {
        return hateoasBuilder.addLinksForTag(tagService.find(name));
    }

    /**
     * a method which find the most popular tag in user with the highest cost of all orders.
     *
     * @return a collection of Tag, which represents a resource "tags" from data base
     */
    @GetMapping("/most-used")
    public List<TagDTO> findMostUsedTag() {
        return tagService.getMostUsedTagOfUserWithHighestCostOfOrders();
    }
}
