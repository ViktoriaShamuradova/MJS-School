package com.epam.esm.controller;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.criteria_info.TagCriteriaInfo;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import com.epam.esm.util.TagHateoasAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * a class which performs REST's CRUD operations on a resource called "Tag"
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;
    private final TagHateoasAssembler tagAssembler;

    public static final String AUTHORITY_READ = "hasAuthority('tag:read')";
    public static final String AUTHORITY_WRITE = "hasAuthority('tag:write')";

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo     - map witch contains information about pagination
     * @param criteriaInfo - object with information about tag to search
     * @return a collection of TagDTO with links, which represents a resource "tag" from database with links
     */
    @PreAuthorize(AUTHORITY_READ)
    @GetMapping
    public ResponseEntity<CollectionModel<TagDTO>> find(@Valid PageInfo pageInfo, @Valid TagCriteriaInfo criteriaInfo) {
        List<TagDTO> tags = tagService.find(pageInfo, criteriaInfo);
        long count = tagService.getCount();
        return ResponseEntity.ok(tagAssembler.toHateoasCollectionOfEntities(tags, pageInfo, count));
    }

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param tag an object which represents a resource "tags" that must be created
     *            in the data source
     * @return an object which represents Http response of CREATE operation
     */
    @PreAuthorize(AUTHORITY_WRITE)
    @PostMapping
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagDTO tag) {
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
    @PreAuthorize(AUTHORITY_WRITE)
    @DeleteMapping("/{tagName}")
    public ResponseEntity<RepresentationModel> delete(@PathVariable @NotBlank @Size(max = 45) String tagName) {
        RepresentationModel representationModel = new RepresentationModel();
        tagAssembler.appendGenericTagHateoasActions(representationModel);
        if (tagService.delete(tagName)) {
            return new ResponseEntity<>(representationModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(representationModel, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * a method which find the most popular tag in user with the highest cost of all orders.
     *
     * @return a collection of Tag, which represents a resource "tags" from data base
     */
    @PreAuthorize(AUTHORITY_READ)
    @GetMapping("/most-used")
    public ResponseEntity<CollectionModel<TagDTO>> findMostUsedTag() {
        List<TagDTO> tags = tagService.getMostUsedTagOfUserWithHighestCostOfOrders();
        return ResponseEntity.ok(tagAssembler.toHateoasCollectionOfEntities(tags, new PageInfo(), tags.size()));
    }
}
