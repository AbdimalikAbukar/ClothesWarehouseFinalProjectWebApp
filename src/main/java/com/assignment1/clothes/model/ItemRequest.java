package com.assignment1.clothes.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String brand;
    private String name;
    private int quantity;

    @Override
    public String toString() {
        return "ItemRequest{" +
                "brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
