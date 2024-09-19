package ua.com.alevel.final_project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegRequest extends ApiRequest {

    @NotBlank(message = "email can't be blank")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "user email is not valid")
    private String email;

    @NotBlank(message = "password can't be blank")
    private String password;

    private String firstName;

    private String lastName;

    @Min(value = 0, message = "Age should be a positive number")
    private Integer age;
}