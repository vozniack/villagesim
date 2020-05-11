package pl.kielce.tu.villageSim.api.dto;

import lombok.Data;

@Data
public class WorldParametersDto {

    /* Size */

    private Integer width = 96;

    private Integer height = 32;

    /* Generating factors */

    private Double treeFactor = 0.1;

    private Double rockFactor = 0.05;

    /* Resources */

    private Integer wood = 64;

    private Integer rock = 32;

    private Integer food = 16;

    private Integer gold = 0;

    /* Units */

    private Integer peasants = 4;

    private Integer workers = 0;
}
