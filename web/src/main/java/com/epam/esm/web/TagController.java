package com.epam.esm.web;

import com.epam.esm.service.TagService;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.web.exceptionHandling.NoSuchResourceException;
import com.epam.esm.web.exceptionHandling.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<TagDTO> showAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping
    public void addNewTag(@RequestBody TagDTO tagDTO) {
        TagDTO t = tagService.createTag(tagDTO);
        if (t == null) {
            throw new TagAlreadyExistsException("such tag with name = " + tagDTO.getName() + " already exists in dataBase");
        }
    }

    @DeleteMapping("/{tagId}")
    public String deleteTag(@PathVariable int tagId) {
        TagDTO tagDTO = tagService.getTag(tagId);
        if (tagDTO == null) {
            throw new NoSuchResourceException("There is no tag with this id = " + tagId + " in dataBase");
        }
        tagService.deleteTag(tagId);
        return "Tag with id= " + tagId + " was deleted";
    }

    @GetMapping("/{name}")
    public TagDTO findTag(@PathVariable("name") String name) {
        TagDTO tag = tagService.getTag(name);
        if (tag == null) {
            throw new NoSuchResourceException("There is no tag with this name = " + name + " in dataBase");
        }
        return tag;
    }
}
