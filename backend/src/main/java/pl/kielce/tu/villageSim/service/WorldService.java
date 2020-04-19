package pl.kielce.tu.villageSim.service;

import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;

@Service
public class WorldService {

    public World getWorldInstance() {
        World world = new World();

        // #todo get all world properties and content

        return world;
    }
}
