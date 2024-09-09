package ua.com.alevel.final_project.dto.response;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.repository.ProductRepository;
@Getter
@Setter
public class ProductResponse extends ApiResponse<Long> {
    private Long id;
    private Product product;
    private Integer count;

    public ProductResponse(ProductInOrder productInOrder,  ProductRepository productRepository) {
        this.id = productInOrder.getId();
        this.count = productInOrder.getCount();
        this.product = productRepository.findById(productInOrder.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + productInOrder.getProductId()));
    }
}
