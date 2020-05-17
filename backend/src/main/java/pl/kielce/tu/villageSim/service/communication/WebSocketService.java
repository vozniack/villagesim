package pl.kielce.tu.villageSim.service.communication;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessageToWebSocket(String topic, Object data) {
        simpMessagingTemplate.convertAndSend(topic, data);
    }
}
