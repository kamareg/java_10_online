package ua.com.alevel.final_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.enums.ProductType;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(nullable = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @Column(length = 4096)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;
}
