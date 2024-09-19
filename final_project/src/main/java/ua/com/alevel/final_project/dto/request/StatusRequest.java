package ua.com.alevel.final_project.dto.request;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.final_project.enums.Status;

@Getter
@Setter
public class StatusRequest {
    private Status status;
}
