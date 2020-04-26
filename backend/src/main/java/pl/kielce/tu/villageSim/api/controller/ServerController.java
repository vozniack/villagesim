package pl.kielce.tu.villageSim.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.villageSim.api.dto.ServerStatusDto;

@RestController
@RequestMapping("/api/server")
public class ServerController {

    @GetMapping("/status")
    public ServerStatusDto getServerStatus() {
        return new ServerStatusDto("OK");
    }
}
