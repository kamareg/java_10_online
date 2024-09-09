package ua.com.alevel.final_project.dto.request;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.Product;
//import ua.com.alevel.final_project.entity.ProductInOrder;


@Getter
@Setter
public class OrderRequest extends ApiRequest{
    private Order order;
    private Product product;
    private Integer count;
//    private Integer count;
}
