package ua.com.alevel.final_project.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;
import ua.com.alevel.final_project.exception.EntityNotFoundException;
import ua.com.alevel.final_project.repository.OrderRepository;
import ua.com.alevel.final_project.service.OrderService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order getCurrentOrder(User currentUser) {
        Order order = orderRepository.findByUserAndStatus(currentUser, Status.NEW);
        if (order == null) {
            order = createNewOrderForUser(currentUser);
        }
        return order;
    }

    @Override
    public Order createNewOrderForUser(User user) {
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setProducts(new HashSet<>());
        newOrder.setCost(new BigDecimal(0));
        newOrder.setStatus(Status.NEW);
        return orderRepository.save(newOrder);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public void create(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void updateStatus(Long orderId, Status status) {
        if (orderId == null) {
            throw new EntityNotFoundException("Order not found");
        }
        Order order = findById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUser_Id(Long id) {
        return orderRepository.findAllByUser_Id(id);
    }
}
