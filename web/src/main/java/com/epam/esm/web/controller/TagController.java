package com.epam.esm.web.controller;

import com.epam.esm.criteria_info.CriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.util.TagHateoasAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

    private final TagService tagService;
    private final TagHateoasAssembler tagAssembler;

    @Autowired
    public TagController(TagService tagService, TagHateoasAssembler tagAssembler) {
        this.tagAssembler = tagAssembler;
        this.tagService = tagService;
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo     - map witch contains information about pagination
     * @param criteriaInfo - object with information about tag to search
     * @return a collection of TagDTO with links, which represents a resource "tag" from database with links
     */
    @GetMapping
    public ResponseEntity<CollectionModel<TagDTO>> find(PageInfo pageInfo, CriteriaInfo criteriaInfo) {
        List<TagDTO> tags = tagService.find(pageInfo, criteriaInfo);
        return ResponseEntity.ok(tagAssembler.toHateoasCollectionOfEntities(tags));
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param tag an object which represents a resource "tags" that must be created
     *            in the data source
     * @return an object which represents Http response of CREATE operation
     */
    @PostMapping
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tag) {
        TagDTO tagNew = tagService.create(tag);
        tagAssembler.appendAsForMainEntity(tagNew);
        return new ResponseEntity<>(tagNew, HttpStatus.CREATED);
    }

    /**
     * a method which realizes REST's DELETE operation of a specific resource with ID stored in a request path
     *
     * @param tagName an identification number of a resource which should be deleted
     * @return an object which represents Http response of DELETE operation
     */
    @DeleteMapping("/{tagName}")
    public ResponseEntity<String> delete(@PathVariable String tagName) {
        if (tagService.delete(tagName)) {
            return ResponseEntity.ok("Tag with name= " + tagName + " was deleted");
        } else {
            return ResponseEntity.ok("Tag with name= " + tagName + " was not found and not deleted");
        }
    }

    /**
     * a method which find the most popular tag in user with the highest cost of all orders.
     *
     * @return a collection of Tag, which represents a resource "tags" from data base
     */
    @GetMapping("/most-used")
    public ResponseEntity<CollectionModel<TagDTO>> findMostUsedTag() {
        List<TagDTO> tags =  tagService.getMostUsedTagOfUserWithHighestCostOfOrders();
        return ResponseEntity.ok(tagAssembler.toHateoasCollectionOfEntities(tags));
    }
}
