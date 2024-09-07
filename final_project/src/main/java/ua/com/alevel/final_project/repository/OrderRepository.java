package ua.com.alevel.final_project.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;

@Repository
public interface OrderRepository extends BaseRepository<Order> {
    Order findByUserAndStatus(User user, Status status);
}
