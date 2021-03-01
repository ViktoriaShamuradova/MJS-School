package com.epam.esm.util;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.web.controller.OrderController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoasAssembler {

    public void appendSelfReference(OrderDto dto) {
        dto.add(linkTo(methodOn(OrderController.class).find(dto.getId())).withSelfRel());
    }

    public CollectionModel<OrderDto> toHateoasCollectionOfEntities(List<OrderDto> orders) {
        orders.forEach(this::appendSelfReference);
        Link selfLink = linkTo(OrderController.class).withSelfRel();
        CollectionModel<OrderDto> collectionModel = CollectionModel.of(orders, selfLink);
        appendGenericOrderHateoasActions(collectionModel);
        return collectionModel;
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
