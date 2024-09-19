package ua.com.alevel.final_project.service;

import ua.com.alevel.final_project.dto.request.OrderRequest;
import ua.com.alevel.final_project.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product findById(Long id);

    void updateProduct(OrderRequest request);

    void deleteById(Long id);

    Product create(Product product);

    boolean existsByProductId(Long id);
}
