package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;
import pl.kielce.tu.villageSim.model.World;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorldScheduledService {
    private final static String WORLD_TOPIC = "/topic/world";

    private final WorldMapper worldMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedDelay = 1024)
    public void sendWorld() {
        if (World.isWorldReady) {
            log.debug("## Trying to send world through WebSocket...");

            simpMessagingTemplate.convertAndSend(WORLD_TOPIC, worldMapper.createWorldDto());
        }
    }
}
