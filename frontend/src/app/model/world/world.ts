import {Unit} from "./unit";
import {Structure} from "./structure";
import {Building} from "./building";

export class World {

  sizeWidth: number = 96;

  sizeHeight: number = 32;

  units: Unit[] = [];

  buildings: Building[] = [];

  structures: Structure[] = [];

  wood: number = 0;

  rock: number = 0;

  food: number = 0;

  unitsAmount: number = 0;

  buildingsAmount: number = 0;
}

