package com.assignment1.clothes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment1.clothes.model.Clothe;

@Repository
public interface ClotheRepository extends JpaRepository<Clothe, Long> {
    Page<Clothe> findAll(Pageable pageable);

    Page<Clothe> findByBrand(Clothe.Brand brand, Pageable pageable);

    @Query("SELECT c FROM Clothe c WHERE c.brand = :brand AND c.yearOfCreation = 2022")
    List<Clothe> findByBrandAndYearOfCreation(Clothe.Brand brand, int yearOfCreation);
}
