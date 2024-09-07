package ua.com.alevel.final_project.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.final_project.entity.ProductInOrder;

import java.util.Optional;

@Repository
public interface ProductInOrderRepository extends BaseRepository<ProductInOrder>{
    Optional<ProductInOrder> findByProductId(Long productId);
}
