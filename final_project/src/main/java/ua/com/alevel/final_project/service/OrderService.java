package ua.com.alevel.final_project.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;
import ua.com.alevel.final_project.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order findActiveOrderByUser(User user) {
        return orderRepository.findByUserAndStatus(user, Status.NEW);
    }

    public Order createNewOrderForUser(User user) {
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setProducts(new HashSet<>());
        newOrder.setCost(new BigDecimal(0));
        newOrder.setStatus(Status.NEW);
        return orderRepository.save(newOrder);
    }
}
