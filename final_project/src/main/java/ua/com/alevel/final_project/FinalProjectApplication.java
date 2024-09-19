package ua.com.alevel.final_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalProjectApplication {
    @Autowired
    B b;

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }
}
