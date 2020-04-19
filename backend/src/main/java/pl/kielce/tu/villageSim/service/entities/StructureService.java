package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.repository.StructureRepository;

@Service
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public void deleteAllStructures() {
        structureRepository.deleteAll();
    }
}
