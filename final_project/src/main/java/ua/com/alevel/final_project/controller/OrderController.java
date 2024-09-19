package ua.com.alevel.final_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.dto.request.OrderRequest;
import ua.com.alevel.final_project.dto.response.OrderResponse;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;
import ua.com.alevel.final_project.service.OrderService;
import ua.com.alevel.final_project.service.ProductService;
import ua.com.alevel.final_project.service.impl.UserDetailsService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tastyBurgers")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserDetailsService userService;
    private final ProductService productService;


    @GetMapping("getProducts")
    public ResponseEntity<List<Product>> findAllFood() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("currentOrder")
    public ResponseEntity<OrderResponse> getCurrentOrder(Principal principal) {
        User currentUser = (User) userService.loadUserByUsername(principal.getName());
        Order order = orderService.getCurrentOrder(currentUser);
        return ResponseEntity.ok(new OrderResponse(order, productService));
    }

    @PostMapping("updateCount")
    public ResponseEntity<?> updateOrder(@RequestBody OrderRequest request) {
        productService.updateProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("updateStatus")
    public ResponseEntity<?> makeOrder(@RequestBody Long orderFromRequest) {
        orderService.updateStatus(orderFromRequest, Status.CREATED);
        return ResponseEntity.ok().build();
    }
}
