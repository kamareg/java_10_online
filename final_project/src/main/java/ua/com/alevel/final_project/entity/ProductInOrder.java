package ua.com.alevel.final_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_in_order")
public class ProductInOrder extends BaseEntity {

    private Long productId;
    private Integer count;

    public ProductInOrder() {
    }
}
