import {ElementRef, Injectable} from '@angular/core';
import {World} from "../../model/world/world";

@Injectable({
  providedIn: 'root'
})
export class CanvasService {

  canvas: ElementRef<HTMLCanvasElement>;
  ctx: CanvasRenderingContext2D;
  worldMapContainer: ElementRef;

  constructor() {

  }

  initCanvas(canvas: ElementRef<HTMLCanvasElement>, ctx: CanvasRenderingContext2D, worldMapContainer: ElementRef) {
    this.canvas = canvas;
    this.ctx = ctx;
    this.worldMapContainer = worldMapContainer;

    this.ctx = this.canvas.nativeElement.getContext("2d");
    this.resize();
  }

  resize() {
    this.ctx.canvas.width = this.worldMapContainer.nativeElement.clientWidth - 2;
    this.ctx.canvas.height = 256 // #todo count what is needed
  }

  drawMap(world: World) {

  }

}
