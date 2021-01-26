package com.epam.esm.web;

import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<TagDTO> findAll() {
        return tagService.findAll();
    }

    @PostMapping
    public void add(@RequestBody TagDTO tagDTO) {
        tagService.create(tagDTO);
    }
//esponseEntity
    @DeleteMapping("/{tagId}")
    public String delete(@PathVariable int tagId) {
        tagService.delete(tagId);
        return "Tag with id= " + tagId + " was deleted";
    }

    @GetMapping("/{name}")
    public TagDTO find(@PathVariable("name") String name) {
        return tagService.find(name);
    }
}
