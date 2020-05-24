package pl.kielce.tu.villageSim.service.communication;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.api.mapper.LogMapper;
import pl.kielce.tu.villageSim.api.mapper.StatisticsMapper;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;
import pl.kielce.tu.villageSim.types.log.LogType;

@Service
@AllArgsConstructor
public class CommunicationService {
    private final static String TOPIC_WORLD = "/topic/world";
    private final static String TOPIC_STATISTICS = "/topic/statistics";
    private final static String TOPIC_LOG = "/topic/log";

    private final WebSocketService webSocketService;

    private final WorldMapper worldMapper;
    private final StatisticsMapper statisticsMapper;
    private final LogMapper logMapper;

    public void sendWorldState() {
        webSocketService.sendMessageToWebSocket(TOPIC_WORLD, worldMapper.createWorldDto());
    }

    public void sendStatistics() {
        webSocketService.sendMessageToWebSocket(TOPIC_STATISTICS, statisticsMapper.createStatisticsDto());
    }

    public void sendLog(String message, String time, LogType logType) {
        webSocketService.sendMessageToWebSocket(TOPIC_LOG, logMapper.createLogDto(message, time, logType));
    }
}
