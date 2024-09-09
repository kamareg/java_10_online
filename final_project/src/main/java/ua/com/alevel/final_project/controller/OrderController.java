package ua.com.alevel.final_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.dto.request.OrderRequest;
import ua.com.alevel.final_project.dto.response.OrderResponse;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.Order;
//import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;
import ua.com.alevel.final_project.repository.OrderRepository;
import ua.com.alevel.final_project.repository.ProductInOrderRepository;
import ua.com.alevel.final_project.repository.ProductRepository;
import ua.com.alevel.final_project.service.OrderService;
import ua.com.alevel.final_project.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tastyBurgers")
@AllArgsConstructor
public class OrderController {
    private final ProductRepository productRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<Product>> findAllFood() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/current")
    public ResponseEntity<OrderResponse> getCurrentOrder(Principal principal) {
        // Получаем текущего аутентифицированного пользователя
        User currentUser = userService.findById(1L);
//                userService.findByEmail(principal.getName());
        Order order = orderService.findActiveOrderByUser(currentUser);
        if (order == null) {
            order = orderService.createNewOrderForUser(currentUser);
        }
        return ResponseEntity.ok(new OrderResponse(order, productRepository));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody OrderRequest request) {
        Order orderFromRequest = request.getOrder();
        Product productInOrderFromRequest = request.getProduct();
        Integer countFromRequest = request.getCount();

        Order order = orderRepository.findById(orderFromRequest.getId()).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        ProductInOrder productInOrder = order.getProducts().stream()
                .filter(pr -> pr.getProductId().equals(productInOrderFromRequest.getId()))
                .findFirst()
                .orElse(null);

        if (productInOrder == null) {
            productInOrder = new ProductInOrder();
            productInOrder.setCount(countFromRequest);
            productInOrder.setProductId(productInOrderFromRequest.getId());
            productInOrderRepository.save(productInOrder);
            order.getProducts().add(productInOrder);
        } else {
            if (countFromRequest == 0) {
                order.getProducts().remove(productInOrder);
                productInOrderRepository.delete(productInOrder);
            } else {
                productInOrder.setCount(countFromRequest);
                productInOrderRepository.save(productInOrder);
                order.getProducts().add(productInOrder);
            }
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (ProductInOrder p : order.getProducts()) {

            BigDecimal count = productRepository.findById(p.getProductId()).get().getPrice().multiply(BigDecimal.valueOf(p.getCount()));
            totalCost = totalCost.add(count);
        }

        order.setCost(totalCost);
        orderRepository.save(order);
        System.out.println(totalCost);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> makeOrder(@RequestBody Order orderFromRequest) {
        if (orderFromRequest.getId() == null) {
            return ResponseEntity.badRequest().body("Order ID must not be null");
        }

        Order order = orderRepository.findById(orderFromRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));


        order.setStatus(Status.CREATED);
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }
}
