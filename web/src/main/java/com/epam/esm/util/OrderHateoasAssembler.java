package com.epam.esm.util;

import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.web.controller.OrderController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoasAssembler {

    private void appendSelfReference(OrderDto dto) {
        dto.add(linkTo(methodOn(OrderController.class).find(dto.getId())).withSelfRel());
    }

    public CollectionModel<OrderDto> toHateoasCollectionOfEntities(List<OrderDto> orders, PageInfo pageInfo,
                                                                   long entityCount) {
        orders.forEach(this::appendSelfReference);
        Link selfLink = linkTo(OrderController.class).withSelfRel();
        CollectionModel<OrderDto> collectionModel = CollectionModel.of(orders, selfLink);
        appendGenericOrderHateoasActions(collectionModel);
        addPageNavigation(collectionModel, pageInfo, entityCount);
        return collectionModel;
    }
    private void addPageNavigation(RepresentationModel dto, PageInfo pageInfo, long entityCount) {
        PageNavigationLinkGenerator pageUrlGenerator= new PageNavigationLinkGenerator(pageInfo, entityCount, "/springboot-rest/orders");
        Map<String, Link> links = pageUrlGenerator.getLinks();

        for(Map.Entry<String, Link> entry : links.entrySet()) {
            dto.add(entry.getValue().withRel(entry.getKey()));
        }
    }
    private void appendGenericOrderHateoasActions(RepresentationModel dto) {
        dto.add(linkTo(OrderController.class).withRel("GET: get all orders"));
        dto.add(linkTo(OrderController.class).withRel("POST: create order"));
    }

    public void appendAsForMainEntity(OrderDto orderDto) {
        appendSelfReference(orderDto);
        appendGenericOrderHateoasActions(orderDto);
    }
}
