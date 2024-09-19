package ua.com.alevel.final_project.dto.response;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.entity.User;
import ua.com.alevel.final_project.enums.Status;
import ua.com.alevel.final_project.service.ProductService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderResponse extends ApiResponse<Long> {
    private Long id;
    private User user;
    private Status status;
    private Set<ProductResponse> products = new HashSet<>();
    private BigDecimal cost;

    public OrderResponse(Order order, ProductService productService) {
        this.id = order.getId();
        this.user = order.getUser();
        this.status = order.getStatus();
        this.cost = order.getCost();
        for (ProductInOrder productInOrder : order.getProducts()) {
            this.products.add(new ProductResponse(productInOrder, productService));
        }
    }
}
