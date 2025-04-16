package com.assignment1.clothes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clothe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Min(1000)
    private Double price;

    @NotNull
    @Min(2022)
    private Integer yearOfCreation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @NotNull
    @Min(0)
    private Integer quantity;

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public enum Brand {
        BALENCIAGA(0, "Balenciaga"),
        STONE_ISLAND(1, "Stone Island"),
        DIOR(2, "Dior"),
        GUCCI(3, "Gucci"),
        PRADA(4, "Prada"),
        VERSACE(5, "Versace"),
        OFF_WHITE(6, "Off-White"),
        FENDI(7, "Fendi"),
        YVES_SAINT_LAURENT(8, "Yves Saint Laurent"),
        ARMANI(9, "Armani"),
        CHANEL(10, "Chanel");

        private final int code;
        private final String name;

        Brand(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getBrandName() {
            return name;
        }

        public static Brand fromCode(int code) {
            for (Brand brand : values()) {
                if (brand.getCode() == code) {
                    return brand;
                }
            }
            throw new IllegalArgumentException("Unknown brand code: " + code);
        }
    }
}
