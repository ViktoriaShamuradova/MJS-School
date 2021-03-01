package com.epam.esm.util;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.web.controller.UserController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UrlGenerator {

    public CollectionModel<UserDTO> addLinksForListOfUsers(List<UserDTO> users, PageInfo pageInfo,
                                                         long userCount, HttpServletRequest request) {
        UriComponentsBuilder baseUri = ServletUriComponentsBuilder.fromServletMapping(request)
                .path(request.getRequestURI());

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                baseUri.queryParam(entry.getKey(), value);
            }
        }

        UriComponentsBuilder original = baseUri;

        users.forEach(userDto -> {
            userDto.add(linkTo(methodOn(UserController.class).find(userDto.getId())).withSelfRel());
        });
        Link allOrderLink = linkTo(UserController.class).withSelfRel();


        long currentPage = pageInfo.getCurrentPage();
        long pageCount = pageInfo.getNumberOfPages();
        CollectionModel<UserDTO> collectionModel = CollectionModel.of(users, allOrderLink);


        if (currentPage < pageCount) {
            pageInfo.setCurrentPage(pageInfo.getCurrentPage() + 1);
            UriComponentsBuilder nextBuilder = replaceParam(original, pageInfo);
           // collectionModel.add(linkTo(nextBuilder.toUriString()).withRel("next"));
            collectionModel.add(linkTo(UserController.class, nextBuilder.toUriString()).withRel("next"));
        }
        if (currentPage > 1) {
//            pageInfo.setCurrentPage(pageInfo.getCurrentPage() - 1);
//            UriComponentsBuilder prevBuilder = replaceParam(original, pageInfo);
//            collectionModel.add(linkTo(prevBuilder.toUriString()).withRel("prev"));
            collectionModel.add(linkTo(UserController.class).withRel("GET: get all orders"));
        }
        return collectionModel;
    }

    private UriComponentsBuilder replaceParam(UriComponentsBuilder original, PageInfo pageInfo) {
        UriComponentsBuilder builder = original.cloneBuilder();
        builder.replaceQueryParam("currentPage", pageInfo.getCurrentPage());
        builder.replaceQueryParam("limit", pageInfo.getLimit());
        builder.replaceQueryParam("numberOfPages", pageInfo.getNumberOfPages());
        return builder;
    }

}
