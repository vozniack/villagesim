package pl.kielce.tu.villageSim.service.communication;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;

@Service
@AllArgsConstructor
public class CommunicationService {
    private final static String WORLD_TOPIC = "/topic/world";

    private final WebSocketService webSocketService;

    private final WorldMapper worldMapper;

    public void sendWorldState() {
        webSocketService.sendMessageToWebSocket(WORLD_TOPIC, worldMapper.createWorldDto());
    }

    public void sendLog() {
        // #todo
    }

    public void sendStatistic() {
        // #todo
    }
}
