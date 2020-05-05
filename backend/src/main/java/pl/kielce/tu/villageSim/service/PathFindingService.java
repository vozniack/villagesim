package pl.kielce.tu.villageSim.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;

@Service
@AllArgsConstructor
public class PathFindingService {

    private Integer[][] prepareArray() {
        Integer[][] array = new Integer[World.sizeWidth][World.sizeHeight];

        for (int i = 0; i < World.sizeWidth; i++) {
            for (int j = 0; j < World.sizeHeight; j++) {
                array[i][j] = 0;
            }
        }

        return array;
    }
}
