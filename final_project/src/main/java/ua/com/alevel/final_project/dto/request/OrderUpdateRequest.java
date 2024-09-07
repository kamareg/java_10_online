package ua.com.alevel.final_project.dto.request;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.ProductInOrder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OrderUpdateRequest extends ApiRequest{
    private Order order;
    private ProductInOrder productInOrder;
}
