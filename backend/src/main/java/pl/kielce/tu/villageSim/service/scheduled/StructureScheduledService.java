package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@RequiredArgsConstructor
public class StructureScheduledService {
    private final CommunicationService communicationService;
    private final StructureRepository structureRepository;

    @Scheduled(fixedDelay = 2048) // 2 seconds
    public void growTrees() {
        if (SchedulerUtil.canPerform()) {
            structureRepository.findAllByStructureTypeAndStructureLevelLessThan(StructureType.TREE, 3).forEach(tree -> {
                if (RandUtil.generateChance(0.05)) {
                    tree.setStructureLevel(tree.getStructureLevel() + 1);
                    structureRepository.save(tree);
                }
            });

            communicationService.sendWorldState();
        }
    }

    @Scheduled(fixedDelay = 2048) // 2 seconds
    public void brokeTrees() {
        if (SchedulerUtil.canPerform()) {
            structureRepository.findAllByStructureTypeAndStructureLevelGreaterThan(StructureType.TREE, 1).forEach(tree -> {
                if (RandUtil.generateChance(0.05)) {
                    tree.setStructureLevel(tree.getStructureLevel() + -1);
                    structureRepository.save(tree);
                }
            });
        }

        communicationService.sendWorldState();
    }
}
