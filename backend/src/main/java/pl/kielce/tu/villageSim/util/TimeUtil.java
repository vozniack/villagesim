package pl.kielce.tu.villageSim.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {

    public static LocalDateTime START_TIME;

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();

        String minutes = String.valueOf(Duration.between(START_TIME, now).toMinutes());

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        String seconds = String.valueOf(Duration.between(START_TIME, now).toSeconds());

        if (Integer.parseInt(seconds) < 0) {
            seconds = "00";
        }

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }
}
