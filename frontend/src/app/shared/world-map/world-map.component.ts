import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {WorldService} from "../../service/world/world.service";
import {World} from "../../model/world/World";

@Component({
  selector: 'app-world-map',
  templateUrl: './world-map.component.html',
  styleUrls: ['./world-map.component.sass']
})
export class WorldMapComponent implements OnInit {

  world: World = new World();

  isGenerated: boolean = false;

  @ViewChild("worldMapContainer", {static: true})
  worldMapContainer: ElementRef;

  @ViewChild("worldMap", {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  ctx: CanvasRenderingContext2D;

  constructor(private worldService: WorldService) {
  }

  ngOnInit(): void {
    this.worldService.getWorld();
    this.initWorldMap();
  }

  generate() {
    this.worldService.generate().subscribe(() => {
      this.isGenerated = true;
    })
  }

  initWorldMap() {
    this.ctx = this.canvas.nativeElement.getContext("2d");
    this.setCanvasSize();
  }

  setCanvasSize() {
    this.ctx.canvas.width = this.worldMapContainer.nativeElement.clientWidth;
    this.ctx.canvas.height = 256
  }


  onResize() {
    this.setCanvasSize();
  }
}
