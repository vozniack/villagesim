package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorldScheduledService {
    private final static String WORLD_TOPIC = "/topic/world";

    private final WorldMapper worldMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StructureService structureService;

    @Scheduled(fixedDelay = 512)
    public void sendWorld() {
        if (SchedulerUtil.canPerform()) {
            log.debug("## Trying to send world through WebSocket...");

            /* #todo to delete, it's just for communication tests
            for (int i = 0; i < 10; i++) {
                structureService.createStructure(StructureType.TREE, RandUtil.generateRand(1, 3), new Position(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
            }
            */

            simpMessagingTemplate.convertAndSend(WORLD_TOPIC, worldMapper.createWorldDto());
        }
    }
}
