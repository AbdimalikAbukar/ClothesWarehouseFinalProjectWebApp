package com.assignment1.clothes.model;

import com.assignment1.clothes.model.Clothe.Brand;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer yearOfCreation;
    private Brand brand;
    private int quantity;

    @Override
    public String toString() {
        return "ItemResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", yearOfCreation=" + yearOfCreation +
                ", brand=" + brand +
                ", quantity=" + quantity +
                '}';
    }
}
