import {Unit} from "./unit";
import {Structure} from "./structure";
import {Building} from "./building";

export class World {

  sizeWidth: number = 64;

  sizeHeight: number = 64;

  units: Unit[] = [];

  buildings: Building[] = [];

  structures: Structure[] = [];
}

