package ua.com.alevel.final_project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest extends ApiRequest {
    private Long order;
    private Long product;
    private Integer count;
}
