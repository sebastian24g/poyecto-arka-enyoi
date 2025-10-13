package com.arka_EP.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlaceOrderCommand {
    private Long customerId;
    private List<Item> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Item {
        private Long productId;
        private int quantity;
        private BigDecimal unitPrice;
    }
}
