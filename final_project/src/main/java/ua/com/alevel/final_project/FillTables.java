package ua.com.alevel.final_project;

import ua.com.alevel.final_project.entity.Product;
import ua.com.alevel.final_project.enums.ProductType;
import ua.com.alevel.final_project.repository.ProductRepository;

import java.math.BigDecimal;

public class FillTables {
//    @Autowired
//    private ProductRepository productRepository;

    public static void main(String[] args) {
        Product drink1 = new Product();
        drink1.setName("Coca-Cola");
        drink1.setDescription("Refreshing soda drink.");
        drink1.setPrice(new BigDecimal("1.50"));
        drink1.setImageUrl("https://example.com/images/coca_cola.jpg");
        drink1.setType(ProductType.DRINK);

        Product drink2 = new Product();
        drink2.setName("Orange Juice");
        drink2.setDescription("Freshly squeezed orange juice.");
        drink2.setPrice(new BigDecimal("2.50"));
        drink2.setImageUrl("https://example.com/images/orange_juice.jpg");
        drink2.setType(ProductType.DRINK);

        Product drink3 = new Product();
        drink3.setName("Mineral Water");
        drink3.setDescription("Pure mineral water.");
        drink3.setPrice(new BigDecimal("1.00"));
        drink3.setImageUrl("https://example.com/images/mineral_water.jpg");
        drink3.setType(ProductType.DRINK);


//        productRepository.save(drink1);
//        productRepository.save(drink2);
//        productRepository.save(drink3);
    }
}
