package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.repository.UnitRepository;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public void deleteAllUnits() {
        unitRepository.deleteAll();
    }
}
