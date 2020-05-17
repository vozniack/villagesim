import {ElementRef, Injectable} from '@angular/core';
import {World} from "../../model/world/world";
import {Structure} from "../../model/world/structure";
import {Unit} from "../../model/world/unit";
import {Building} from "../../model/world/building";
import {PathNode} from "../../model/world/pathNode";

@Injectable({
  providedIn: 'root'
})
export class CanvasService {

  canvas: ElementRef<HTMLCanvasElement>;
  ctx: CanvasRenderingContext2D;
  worldMapContainer: ElementRef;

  tileSize: number = 16;

  /* Static rgba colors */

  groundColor: string = 'rgba(93, 64, 55, 0.1)';
  treeColor: string = 'rgba(46, 125, 50,'; // alpha canal is filled by method
  rockColor: string = 'rgba(117, 117, 117,'; // alpha canal is filled by method

  buildingColor: string = 'rgba(135, 0, 0, 1.0)';
  farmColor: string = 'rgba(253, 216, 53, 1.0)';

  unitColor: string = 'rgba(0, 188, 212, 1.0)';

  constructor() {

  }

  initCanvas(canvas: ElementRef<HTMLCanvasElement>, ctx: CanvasRenderingContext2D, worldMapContainer: ElementRef) {
    this.canvas = canvas;
    this.ctx = ctx;
    this.worldMapContainer = worldMapContainer;

    this.ctx = this.canvas.nativeElement.getContext("2d");
    this.resize(null);
  }

  /* Drawing */

  drawMap(world: World) {
    this.countSizes(world.sizeWidth)
    this.resize(world.sizeHeight);
    this.drawWorld(world.sizeWidth, world.sizeHeight);
    this.drawStructures(world.structures);
    this.drawBuildings(world.buildings);
    this.drawUnits(world.units);
  }

  countSizes(worldSizeWidth: number) {
    this.tileSize = this.ctx.canvas.width / worldSizeWidth;
  }

  resize(worldSizeHeight: number) {
    this.ctx.canvas.width = this.worldMapContainer.nativeElement.clientWidth;
    this.ctx.canvas.height = (worldSizeHeight != null) ? worldSizeHeight * this.tileSize : 256;
  }

  drawWorld(sizeWidth: number, sizeHeight: number) {
    this.ctx.fillStyle = this.groundColor;

    for (let x = 0; x <= sizeWidth; x++) {
      for (let y = 0; y <= sizeHeight; y++) {
        this.drawRectangle(x * this.tileSize, y * this.tileSize, this.tileSize - 2, this.tileSize - 2, true);
      }
    }

    this.ctx.fillStyle = 'white'
  }

  drawStructures(structures: Structure[]) {
    structures.forEach(structure => {
      this.setStructureColor(structure.structureType, structure.structureLevel);
      this.drawRectangle(structure.positionX * this.tileSize, structure.positionY * this.tileSize, this.tileSize - 1, this.tileSize - 1, true);
    })
  }

  drawBuildings(building: Building[]) {
    building.forEach(building => {
      this.setBuildingColor(building.buildingType);
      this.drawRectangle(building.positionX * this.tileSize, building.positionY * this.tileSize, (this.tileSize * building.size) - 1, (this.tileSize * building.size) - 1, building.buildingState !== 'PLAN');
    })
  }

  drawUnits(units: Unit[]) {
    units.forEach(unit => {
      this.setUnitColor();
      this.drawRectangle((unit.positionX * this.tileSize) + (this.tileSize * 0.18), (unit.positionY * this.tileSize) + (this.tileSize * 0.18), this.tileSize * 0.55, this.tileSize * 0.55, true);
    })
  }

  /* Temporary drawing */

  drawPath(pathNodes: PathNode[]) {
    pathNodes.forEach(pathNode => {
      this.ctx.fillStyle = 'rgba(239, 83, 80, 1.0)';
      this.drawRectangle((pathNode.x * this.tileSize) + (this.tileSize * 0.18), (pathNode.y * this.tileSize) + (this.tileSize * 0.18), this.tileSize * 0.55, this.tileSize * 0.55, true);
    })
  }

  /* Support methods */

  drawRectangle(x, y, width, height, fill: boolean) {
    if (fill) {
      this.ctx.fillRect(x, y, width, height);
    } else {
      this.ctx.strokeRect(x, y, width, height);
    }

  }

  drawCircle(x, y, radius) {
    this.ctx.arc(x, y, radius, 0, Math.PI, true);
  }

  /* Colors methods */

  setStructureColor(structureType: string, structureLevel: number) {
    switch (structureType) {
      case 'TREE':
        this.ctx.fillStyle = this.treeColor + ((0.33 * structureLevel) + 0.01).toString() + ')';
        break;

      case 'ROCK':
        this.ctx.fillStyle = this.rockColor + ((0.33 * structureLevel) + 0.01).toString() + ')';
        break;
    }
  }

  setBuildingColor(buildingType: string) {
    if (buildingType == 'FARM') {
      this.ctx.fillStyle = this.farmColor;
      this.ctx.strokeStyle = this.farmColor;
    } else {
      this.ctx.fillStyle = this.buildingColor;
      this.ctx.strokeStyle = this.buildingColor;
    }
  }

  setUnitColor() {
    this.ctx.fillStyle = this.unitColor;
  }
}
