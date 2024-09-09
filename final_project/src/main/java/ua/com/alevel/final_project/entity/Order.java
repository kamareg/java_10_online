package ua.com.alevel.final_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.enums.Status;

import java.math.BigDecimal;
import java.util.*;

//@Getter
//@Setter
//@Entity
//@Table(name = "orders")
//public class Order extends BaseEntity {
//    @ManyToOne
//    @JoinTable(
//            name = "users_orders",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private User user;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Status status;
//
//    @OneToMany
//    @JoinTable(
//            name = "products_orders",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private Set<ProductInOrder> products = new HashSet<>();
//
//    @Column(nullable = false)
//    @Digits(integer = 6, fraction = 2)
//    private BigDecimal cost;
//
//    public Order() {
//        this.setStatus(ua.com.alevel.final_project.enums.Status.NEW);
//    }
//}
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinTable(
            name = "users_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany
    @JoinTable(
            name = "products_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductInOrder> products = new HashSet<>();

    // Map to store Product as key and its count as value
//    @ElementCollection
//    @CollectionTable(name = "products_orders", joinColumns = @JoinColumn(name = "order_id"))
//    @MapKeyJoinColumn(name = "product_id")  // Product as key
//    @Column(name = "product_count")  // Count as value
//    private ProductInOrder products = new ProductInOrder();
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<ProductInOrder> products = new ArrayList<>();

    @Column(nullable = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal cost;

    public Order() {
        this.setStatus(Status.NEW);
    }
}