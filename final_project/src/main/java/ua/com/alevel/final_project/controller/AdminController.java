package ua.com.alevel.final_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.dto.request.StatusRequest;
import ua.com.alevel.final_project.dto.UserDto;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.service.OrderService;
import ua.com.alevel.final_project.service.ProductService;
import ua.com.alevel.final_project.service.impl.UserDetailsService;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final OrderService orderService;
    private final UserDetailsService userService;
    private final ProductService productService;

    @GetMapping("orders")
    public ResponseEntity<List<Order>> findAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        List<Order> orders = orderService.findAllByUser_Id(id);
        if (!orders.isEmpty()) {
            return ResponseEntity.badRequest().body("User has active orders and cannot be deleted");
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("orders/{id}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody StatusRequest status) {
        orderService.updateStatus(id, status.getStatus());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.create(product));
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (productService.existsByProductId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product is associated with existing orders and cannot be deleted.");
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
