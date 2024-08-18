package ua.com.alevel.dto;

import lombok.Data;
import ua.com.alevel.type.OrderType;

@Data
public class DataTableRequest {
    private int page;
    private int size;
    private String column;
    private OrderType orderType;

    public DataTableRequest() {
        this.page = 1;
        this.size = 10;
        this.column = "id";
        this.orderType = OrderType.ASC;
    }
}
