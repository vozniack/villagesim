package pl.kielce.tu.villageSim.service.communication;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.api.mapper.ResourcesMapper;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;
import pl.kielce.tu.villageSim.types.resource.ResourceType;

@Service
@AllArgsConstructor
public class CommunicationService {
    private final static String WORLD_TOPIC = "/topic/world";
    private final static String RESOURCES_TOPIC = "/topic/resource";

    private final WebSocketService webSocketService;

    private final WorldMapper worldMapper;
    private final ResourcesMapper resourcesMapper;

    public void sendWorldState() {
        webSocketService.sendMessageToWebSocket(WORLD_TOPIC, worldMapper.createWorldDto());
    }

    public void sendResources(ResourceType resourceType, Integer resourceAmount) {
        webSocketService.sendMessageToWebSocket(RESOURCES_TOPIC, resourcesMapper.createResourceDto(resourceType, resourceAmount));
    }

    public void sendStatistic() {
        // #todo
    }
}
