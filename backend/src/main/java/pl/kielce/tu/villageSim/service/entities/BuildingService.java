package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.repository.BuildingRepository;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public void deleteAllBuildings() {
        buildingRepository.deleteAll();
    }
}
