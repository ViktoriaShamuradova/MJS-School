package com.epam.esm;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exceptionHandling.NoSuchException;
import com.epam.esm.exceptionHandling.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public List<TagDTO> showAllTags() {
        return tagService.getAllTags();
    }

    @PostMapping("/tags")
    public void addNewTag(@RequestBody TagDTO tagDTO) {
        TagDTO t = tagService.createTag(tagDTO);
        if (t == null) {
            throw new TagAlreadyExistsException("such tag with name = " + tagDTO.getName() + " already exists in dataBase");
        }
    }

    @DeleteMapping("/tags/{tagId}")
    public String deleteTag(@PathVariable int tagId) {
        TagDTO tagDTO = tagService.getTag(tagId);
        if (tagDTO == null) {
            throw new NoSuchException("There is no tag with this id = " + tagId + " in dataBase");
        }

        tagService.deleteTag(tagId);
        return "Tag with id= " + tagId + " was deleted";
    }

    @GetMapping("/tags/{tagId}")
    public TagDTO tag(@PathVariable int tagId) {
        TagDTO tagDTO = tagService.getTag(tagId);
        if (tagDTO == null) {
            throw new NoSuchException("There is no tag with this id = " + tagId + " in dataBase");
        }
        return tagDTO;
    }
}
