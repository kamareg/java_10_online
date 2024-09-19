package ua.com.alevel.final_project.service;

import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;

import java.util.List;

public interface OrderService {
    Order getCurrentOrder(User currentUser);

    Order createNewOrderForUser(User user);

    Order findById(Long id);

    void create(Order order);

    void updateStatus(Long orderId, Status status);

    List<Order> findAllOrders();

    List<Order> findAllByUser_Id(Long id);
}
