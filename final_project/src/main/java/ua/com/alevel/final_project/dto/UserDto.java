package ua.com.alevel.final_project.dto;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.entity.User;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Integer age;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().name();
        this.age = user.getAge();
    }
}
