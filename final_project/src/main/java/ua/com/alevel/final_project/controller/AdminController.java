package ua.com.alevel.final_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.final_project.entity.Product;

import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.ProductType;
import ua.com.alevel.final_project.repository.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final ProductRepository productRepository;

    @GetMapping("products")
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("products")
    public ResponseEntity<Void> createProduct(@RequestParam(defaultValue = "name") String name,
                                                 @RequestParam(defaultValue = "description") String description,
                                                 @RequestParam(defaultValue = "0") BigDecimal price,
                                                 @RequestParam(defaultValue = "url") String imageUrl,
                                              @RequestParam(defaultValue = "BURGER") String type) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        switch (type) {
            case "FAST_FOOD" -> product.setType(ProductType.FAST_FOOD);
            case "BURGER" -> product.setType(ProductType.BURGER);
            case "DRINK" -> product.setType(ProductType.DRINK);
        }
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    private final DrinkRepository drinkRepository;
//    private final BurgerRepository burgerRepository;
//    private final FastFoodRepository fastFoodRepository;
//    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//
//    @GetMapping("drinks")
//    public ResponseEntity<List<Drink>> findAllDrinks() {
//        return ResponseEntity.ok(drinkRepository.findAll());
//    }
//
//    @PostMapping("drinks")
//    public ResponseEntity<Void> createDrink(@RequestParam(defaultValue = "name") String name,
//                                                 @RequestParam(defaultValue = "description") String description,
//                                                 @RequestParam(defaultValue = "0") BigDecimal price,
//                                                 @RequestParam(defaultValue = "url") String imageUrl) {
//        Drink drink = new Drink();
//        drink.setName(name);
//        drink.setDescription(description);
//        drink.setPrice(price);
//        drink.setImageUrl(imageUrl);
//        drinkRepository.save(drink);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("burgers")
//    public ResponseEntity<List<Burger>> findAllBurgers() {
//        return ResponseEntity.ok(burgerRepository.findAll());
//    }
//
//    @PostMapping("burgers")
//    public ResponseEntity<Void> createBurger(@RequestParam(defaultValue = "name") String name,
//                                            @RequestParam(defaultValue = "description") String description,
//                                            @RequestParam(defaultValue = "0") BigDecimal price,
//                                            @RequestParam(defaultValue = "url") String imageUrl) {
//        Burger burger = new Burger();
//        burger.setName(name);
//        burger.setDescription(description);
//        burger.setPrice(price);
//        burger.setImageUrl(imageUrl);
//        burgerRepository.save(burger);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("fastFood")
//    public ResponseEntity<List<FastFood>> findAllFastFood() {
//        return ResponseEntity.ok(fastFoodRepository.findAll());
//    }
//
//    @PostMapping("fastFood")
//    public ResponseEntity<Void> createFastFood(@RequestParam(defaultValue = "name") String name,
//                                               @RequestParam(defaultValue = "description") String description,
//                                               @RequestParam(defaultValue = "0") BigDecimal price,
//                                               @RequestParam(defaultValue = "url") String imageUrl) {
//        FastFood fastFood = new FastFood();
//        fastFood.setName(name);
//        fastFood.setDescription(description);
//        fastFood.setPrice(price);
//        fastFood.setImageUrl(imageUrl);
//        fastFoodRepository.save(fastFood);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("orders")
//    public ResponseEntity<List<Order>> findAllOrders() {
//        return ResponseEntity.ok(orderRepository.findAll());
//    }
//
//    @GetMapping("users")
//    public ResponseEntity<List<User>> findAllUsers() {
//        return ResponseEntity.ok(userRepository.findAll());
//    }
}
