package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@RequiredArgsConstructor
public class StructureScheduledService {
    private final StructureService structureService;

    @Scheduled(fixedRate = 60000) // 1 minute
    public void growTrees() {
        if (SchedulerUtil.canPerform()) {
            structureService.getAllTreesToGrow().forEach(tree -> {
                if (RandUtil.generateChance(0.05)) {
                    tree.setStructureLevel(tree.getStructureLevel() + 1);
                    structureService.updateStructure(tree);
                }
            });
        }
    }

    @Scheduled(fixedRate = 60000) // 1 minute
    public void brokeTrees() {
        if (SchedulerUtil.canPerform()) {
            structureService.getAllStructuresByType(StructureType.TREE).forEach(tree -> {
                if (RandUtil.generateChance(0.05) && tree.getStructureLevel() > 1) {
                    tree.setStructureLevel(tree.getStructureLevel() + - 1);
                    structureService.updateStructure(tree);
                }
            });
        }
    }
}
