package pl.kielce.tu.villageSim.api.mapper;

import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.LogDto;
import pl.kielce.tu.villageSim.types.log.LogType;
import pl.kielce.tu.villageSim.util.TimeUtil;

@Component
public class LogMapper {

    public LogDto createLogDto(String message, String time, LogType logType) {
        LogDto logDto = new LogDto();

        logDto.setMessage(message);
        logDto.setTime((time != null) ? time : TimeUtil.getCurrentTime());
        logDto.setStatus(logType.toString().toLowerCase());

        return logDto;
    }
}
