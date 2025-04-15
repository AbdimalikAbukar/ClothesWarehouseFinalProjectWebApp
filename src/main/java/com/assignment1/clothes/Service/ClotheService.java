package com.assignment1.clothes.Service;

import com.assignment1.clothes.model.Clothe;
import java.util.List;

public interface ClotheService {
    List<Clothe> getAllClothes();

    void deleteClotheById(Long id);

    Clothe getClotheById(Long clotheId);

    // ✅ New method
    List<Clothe> getAllClothesFromDistributionCenters();
}
