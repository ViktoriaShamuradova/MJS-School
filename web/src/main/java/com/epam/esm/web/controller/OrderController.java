package com.epam.esm.web.controller;

import com.epam.esm.dto.*;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * class that represents the implementation of operations on a resource called "order"
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/form")
    public ResponseEntity<OrderReadDto> create(@RequestBody CartContext cart, UriComponentsBuilder builder) {
        OrderReadDto order = orderService.create(cart);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> find(@PathVariable long id){
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    /**
     * a method which realizes REST's READ operation of resources by parameters
     *
     * @return a collection of OrderReadDto, which represents a resource "order" from data base
     */

    @GetMapping
    public List<OrderReadDto> find(@RequestParam Map<String, String> params) {
        return orderService.find(params);
    }


//    @GetMapping
//    public ResponseEntity<CartContext> getCart(){
//        CartContext cart = new CartContext();
//        Set< CartItem> cartItems = new HashSet<>();
//        CertificateDTO c = new CertificateDTO();
//        c.setId(1L);
//        c.setPrice(new BigDecimal(100));
//
//        CertificateDTO c2 = new CertificateDTO();
//        c2.setId(2L);
//        c2.setPrice(new BigDecimal(250));
//
//        cartItems.add(new CartItem(c,2));
//        cartItems.add(new CartItem(c2, 3));
//
//        cart.setCartItems(cartItems);
//
//        return new ResponseEntity<>(cart, HttpStatus.OK);
//    }

}
