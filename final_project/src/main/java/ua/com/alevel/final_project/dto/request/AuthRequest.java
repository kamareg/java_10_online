package ua.com.alevel.final_project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest extends ApiRequest {

    private String email;
    private String password;
}
