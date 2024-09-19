package ua.com.alevel.final_project.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.final_project.entity.ProductInOrder;

@Repository
public interface ProductInOrderRepository extends BaseRepository<ProductInOrder> {
    boolean existsByProductId(Long productId);
}
