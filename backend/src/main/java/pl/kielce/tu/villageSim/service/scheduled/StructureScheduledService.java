package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.entities.StructureService;

@Component
@RequiredArgsConstructor
public class StructureScheduledService {
    private final StructureService structureService;

    @Scheduled(fixedRate = 60000) // 1 minute
    public void growTrees() {

        // #todo set structureLevel++ to each structure of type TREE
    }
}
