package ua.com.alevel.final_project.dto.response;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.service.ProductService;

@Getter
@Setter

public class ProductResponse extends ApiResponse<Long> {
    private Long id;
    private Product product;
    private Integer count;
    private final ProductService productService;

    public ProductResponse(ProductInOrder productInOrder, ProductService productService) {
        this.id = productInOrder.getId();
        this.count = productInOrder.getCount();
        this.productService = productService;
        this.product = productService.findById(productInOrder.getProductId());
    }
}
