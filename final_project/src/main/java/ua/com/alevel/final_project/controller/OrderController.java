package ua.com.alevel.final_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.dto.request.OrderUpdateRequest;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.repository.OrderRepository;
import ua.com.alevel.final_project.repository.ProductInOrderRepository;
import ua.com.alevel.final_project.repository.ProductRepository;
import ua.com.alevel.final_project.service.OrderService;
import ua.com.alevel.final_project.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/tastyBurgers")
@AllArgsConstructor
public class OrderController {
    private final ProductRepository productRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserService userService; // Сервис для работы с пользователями


    @GetMapping
    public ResponseEntity<List<Product>> findAllFood() {
//        List<ProductInOrder> productInOrderList = productRepository.findAll()
//                .stream().map(product -> new ProductInOrder(product, 0)).toList();
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/current")
    public ResponseEntity<Order> getCurrentOrder(Principal principal) {
        // Получаем текущего аутентифицированного пользователя
        User currentUser = userService.findById(1L);
//                userService.findByEmail(principal.getName());

        Order order = orderService.findActiveOrderByUser(currentUser);

        if (order == null) {
            order = orderService.createNewOrderForUser(currentUser);
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateRequest request) {
        Order orderFromRequest = request.getOrder();
        ProductInOrder productInOrderFromRequest = request.getProductInOrder();
        Order order = orderRepository.findById(orderFromRequest.getId()).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        ProductInOrder productInOrder = productInOrderRepository.findByProductId(productInOrderFromRequest.getProduct().getId())
                .orElse(null);

        if (productInOrder == null) {
            productInOrderRepository.save(productInOrderFromRequest);
            order.getProducts().add(productInOrderFromRequest);
        } else {
            if (productInOrderFromRequest.getCount() == 0) {
                order.getProducts().remove(productInOrder);
                productInOrderRepository.delete(productInOrder);
            } else {
                productInOrder.setCount(productInOrderFromRequest.getCount());
                productInOrderRepository.save(productInOrder);
                order.getProducts().add(productInOrder);
            }
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (ProductInOrder p : order.getProducts()) {
            BigDecimal count = p.getProduct().getPrice().multiply(BigDecimal.valueOf(p.getCount()));
            totalCost = totalCost.add(count);
        }

        order.setCost(totalCost);
        orderRepository.save(order);
        System.out.println(totalCost);
        return ResponseEntity.ok().build();
    }
}
