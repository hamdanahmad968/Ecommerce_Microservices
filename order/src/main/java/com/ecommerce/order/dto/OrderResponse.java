package com.ecommerce.order.dto;
import com.ecommerce.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private BigDecimal orderTotal;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;

    @CreationTimestamp
    private LocalDateTime CreatedAt;
}
