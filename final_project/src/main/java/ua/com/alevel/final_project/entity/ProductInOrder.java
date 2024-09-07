package ua.com.alevel.final_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_in_order")
public class ProductInOrder extends BaseEntity {
//    @OneToOne
//    @JoinTable(
//            name = "product_in_order",
//            joinColumns = @JoinColumn(name = "current_product_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
@ManyToOne
@JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Integer count;

    public ProductInOrder() {
    }

    public ProductInOrder(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }
}
