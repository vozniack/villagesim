import {ElementRef, Injectable} from '@angular/core';
import {World} from "../../model/world/world";
import {Structure} from "../../model/world/structure";
import {Unit} from "../../model/world/unit";
import {Building} from "../../model/world/building";

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
  treeColor: string = 'rgba(46, 125, 50,';
  rockColor: string = 'rgba(117, 117, 117, 1.0)';

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
    // this.drawBuildings(world.buildings);
    // this.drawUnits(world.units);
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
        this.drawRectangle(x * this.tileSize, y * this.tileSize, this.tileSize - 2, this.tileSize - 2);
      }
    }

    this.ctx.fillStyle = 'white'
  }

  drawStructures(structures: Structure[]) {
    structures.forEach(structure => {
      if (structure.structureType === 'TREE') {
        this.ctx.fillStyle = this.treeColor + ((0.33 * structure.structureLevel) + 0.01).toString() + ')';
      } else if (structure.structureType === 'ROCK') {
        this.ctx.fillStyle = this.rockColor;
      }

      this.drawRectangle(structure.positionX * this.tileSize, structure.positionY * this.tileSize, this.tileSize - 1, this.tileSize - 1);
    })
  }

  drawBuildings(building: Building[]) {
    building.forEach(building => {

    })
  }

  drawUnits(units: Unit[]) {
    units.forEach(unit => {

    })
  }

  /* Support methods */

  drawRectangle(x, y, width, height) {
    this.ctx.fillRect(x, y, width, height);
  }

}
