package com.assignment1.clothes.Service;

import com.assignment1.clothes.client.DistributionCentreClient;
import com.assignment1.clothes.model.Clothe;
import com.assignment1.clothes.model.DistributionCenter;
import com.assignment1.clothes.repository.ClotheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClotheServiceImpl implements ClotheService {

    @Autowired
    private ClotheRepository clotheRepository;

    @Autowired
    private DistributionCentreClient distributionCentreClient;

    @Override
    public List<Clothe> getAllClothes() {
        return clotheRepository.findAll();
    }

    @Override
    public void deleteClotheById(Long id) {
        clotheRepository.deleteById(id);
    }

    @Override
    public Clothe getClotheById(Long clotheId) {
        return clotheRepository.findById(clotheId).orElse(null);
    }

    @Override
    public List<Clothe> getAllClothesFromDistributionCenters() {
        List<DistributionCenter> distributionCenters = distributionCentreClient.getAllDistributionCentres();
        return distributionCenters.stream()
                .flatMap(dc -> dc.getItemsAvailable().stream())
                .collect(Collectors.toList());
    }
}
