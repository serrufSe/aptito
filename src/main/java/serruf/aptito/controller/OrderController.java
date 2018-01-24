package serruf.aptito.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serruf.aptito.model.Order;
import serruf.aptito.repository.OrderRepository;
import serruf.aptito.service.OrderService;
import serruf.aptito.specification.OrderSpecification;

import java.util.List;


@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class.getName());

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @RequestMapping(path = "/order", method = RequestMethod.POST)
    public Order create(@RequestBody Order order) {
        log.info("Create order " + order);
        return orderRepository.save(order);
    }

    @RequestMapping(path = "/order/{orderId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("orderId") Long orderId, @RequestBody Order order) {
        order.setId(orderId);

        log.info("Update order " + order);

        orderService.updateOrder(order);
    }

    @RequestMapping(path = "/orders")
    public List<Order> orders(@RequestParam(required = false, name = "completed") Boolean completed,
                              @RequestParam(required = false, name = "name") String name) {
        Specifications<Order> specifications = Specifications.where((root, query, cb) -> cb.conjunction());

        if (completed != null) {
            specifications = specifications.and(OrderSpecification.completedQuery(completed));
        }

        if (name != null) {
            specifications = specifications.and(OrderSpecification.nameQuery(name));
        }

        return orderRepository.findAll(specifications);
    }

}