package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@RequiredArgsConstructor
public class StructureScheduledService {
    private final StructureService structureService;
    private final CommunicationService communicationService;
    private final StructureRepository structureRepository;

    @Scheduled(fixedDelay = 60000) // 1 minute
    public void growTrees() {
        if (SchedulerUtil.canPerform()) {
            structureRepository.findAllByStructureTypeAndStructureLevelLessThan(StructureType.TREE, 3).forEach(tree -> {
                if (RandUtil.generateChance(0.05)) {
                    tree.setStructureLevel(tree.getStructureLevel() + 1);
                    structureService.updateStructure(tree);
                }
            });

            communicationService.sendWorldState();
        }
    }

    @Scheduled(fixedDelay = 60000) // 1 minute
    public void brokeTrees() {
        if (SchedulerUtil.canPerform()) {
            structureRepository.findAllByStructureType(StructureType.TREE).forEach(tree -> {
                if (RandUtil.generateChance(0.05) && tree.getStructureLevel() > 1) {
                    tree.setStructureLevel(tree.getStructureLevel() + -1);
                    structureService.updateStructure(tree);
                }
            });
        }

        communicationService.sendWorldState();
    }
}
