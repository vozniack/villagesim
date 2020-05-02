import {Unit} from "./Unit";
import {Structure} from "./Structure";
import {Building} from "./Building";

export class World {

  sizeWidth: number = 64;

  sizeHeight: number = 64;

  units: Unit[] = [];

  buildings: Building[] = [];

  structures: Structure[] = [];

  constructor() {
  }
}

