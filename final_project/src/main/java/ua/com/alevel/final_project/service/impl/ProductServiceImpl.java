package ua.com.alevel.final_project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.alevel.final_project.dto.request.OrderRequest;
import ua.com.alevel.final_project.entity.Order;
import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.entity.ProductInOrder;
import ua.com.alevel.final_project.exception.EntityNotFoundException;
import ua.com.alevel.final_project.repository.ProductInOrderRepository;
import ua.com.alevel.final_project.repository.ProductRepository;
import ua.com.alevel.final_project.service.OrderService;
import ua.com.alevel.final_project.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductInOrderRepository productInOrderRepository;
    private final OrderService orderService;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(OrderRequest request) {
        Integer countFromRequest = request.getCount();
        Order order = orderService.findById(request.getOrder());
        Product product = findById(request.getProduct());

        ProductInOrder productInOrder = order.getProducts().stream().filter(pr -> pr.getProductId().equals(request.getProduct())).findFirst().orElse(null);

        if (productInOrder == null) {
            productInOrder = new ProductInOrder();
            productInOrder.setCount(countFromRequest);
            productInOrder.setProductId(product.getId());
            productInOrderRepository.save(productInOrder);
            order.getProducts().add(productInOrder);
        } else {
            if (countFromRequest == 0) {
                order.getProducts().remove(productInOrder);
                productInOrderRepository.delete(productInOrder);
            } else {
                productInOrder.setCount(countFromRequest);
                productInOrderRepository.save(productInOrder);
                order.getProducts().add(productInOrder);
            }
        }

        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (ProductInOrder p : order.getProducts()) {
            BigDecimal count = findById(p.getProductId()).getPrice().multiply(BigDecimal.valueOf(p.getCount()));
            totalCost = totalCost.add(count);
        }

        order.setCost(totalCost);
        orderService.create(order);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean existsByProductId(Long id) {
        return productInOrderRepository.existsByProductId(id);
    }

}
