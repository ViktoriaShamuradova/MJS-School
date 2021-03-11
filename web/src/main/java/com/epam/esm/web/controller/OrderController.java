package com.epam.esm.web.controller;

import com.epam.esm.criteria_info.OrderCriteriaInfo;
import com.epam.esm.criteria_info.PageInfo;
import com.epam.esm.dto.Cart;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.OrderHateoasAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * a class which performs GET, CREATE operations on a resource called "order"
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderHateoasAssembler orderAssembler;

    /**
     * a method which realizes REST's CREATE operation
     *
     * @param cart an object which contains data for the formation of a new order
     *             and saving the order in the database
     * @return a responseEntity with status code and target resource order, which represents a resource "order" from database
     * with links
     */
    @PostMapping()
    public ResponseEntity<OrderDto> create(@RequestBody Cart cart) {
        OrderDto order = orderService.create(cart);
        orderAssembler.appendAsForMainEntity(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * a method which realizes REST's READ operation of all resources
     *
     * @param pageInfo          - object witch contains information about pagination
     * @param orderCriteriaInfo - object with information about order to search
     * @return a collection of OrderDTO, which represents a resource "order" from database
     */
    @GetMapping
    public ResponseEntity<CollectionModel<OrderDto>> find(PageInfo pageInfo, OrderCriteriaInfo orderCriteriaInfo) {
        List<OrderDto> orders = orderService.find(pageInfo, orderCriteriaInfo);
        long count = orderService.getCount();
        return ResponseEntity.ok(orderAssembler.toHateoasCollectionOfEntities(orders, pageInfo, count));
    }

    /**
     * a method which realizes REST's READ operation of a specific resource with id stored in a request path
     *
     * @param id an identification number of a requested resource
     * @return an ResponseEntity with OrderDTO wich contains links
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> find(@PathVariable long id) {
        OrderDto order = orderService.findById(id);
        orderAssembler.appendAsForMainEntity(order);
        return ResponseEntity.ok(order);
    }
}
