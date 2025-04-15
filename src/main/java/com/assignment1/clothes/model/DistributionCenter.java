package com.assignment1.clothes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DistributionCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @NotBlank(message = "Name cannot be blank!")
    private String name;

    public String getName() {
        return name;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<Clothe> itemsAvailable;

    public List<Clothe> getItemsAvailable() {
        return itemsAvailable;
    }

    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private double latitude;

    public double getLatitude() {
        return latitude;
    }

    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private double longitude;

    public double getLongitude() {
        return longitude;
    }
}
