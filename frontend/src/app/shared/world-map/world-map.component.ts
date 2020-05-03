import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {WorldService} from "../../service/world/world.service";
import {World} from "../../model/world/world";

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
    this.worldService.world$.subscribe((value: World) => {
      if (this.isGenerated) {
        this.parseJson(value);
      }
    })
  }

  ngOnInit(): void {
    this.initWorldMap();
  }

  parseJson(value: any) {
    this.world.sizeWidth = JSON.parse(value).sizeWidth;
    this.world.sizeHeight = JSON.parse(value).sizeHeight;

    this.world.units = JSON.parse(value).units;
    this.world.buildings = JSON.parse(value).buildings;
    this.world.structures = JSON.parse(value).structures;
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