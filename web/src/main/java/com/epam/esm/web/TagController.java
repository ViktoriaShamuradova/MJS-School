package com.epam.esm.web;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService){
        this.tagService=tagService;
    }

    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @PostMapping
    public void add(@RequestBody Tag tag) {
        tagService.create(tag);
    }

    @DeleteMapping("/{tagId}")
    public String delete(@PathVariable long tagId) {
        tagService.delete(tagId);
        return "Tag with id= " + tagId + " was deleted";
    }

    @GetMapping("/{name}")
    public Tag find(@PathVariable("name") String name) {
        return tagService.find(name);
    }
}
