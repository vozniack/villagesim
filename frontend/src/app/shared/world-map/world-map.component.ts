import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {WorldService} from "../../service/world/world.service";
import {World} from "../../model/world/world";
import {CanvasService} from "../../service/canvas/canvas.service";
import {animate, style, transition, trigger} from "@angular/animations";
import {MatDialog} from "@angular/material/dialog";
import {ModalGenerate} from "../modals/generate-modal/modal-generate.component";
import {WorldParameters} from "../../model/others/worldParameters";

@Component({
  selector: 'app-world-map',
  templateUrl: './world-map.component.html',
  styleUrls: ['./world-map.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class WorldMapComponent implements OnInit {

  world: World = new World();

  isActive: boolean = false;
  isGenerated: boolean = false;
  wasFirstGenerated: boolean = false;

  worldParameters: WorldParameters = new WorldParameters();

  @ViewChild("worldMapContainer", {static: true})
  worldMapContainer: ElementRef;

  @ViewChild("worldMap", {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  ctx: CanvasRenderingContext2D;

  constructor(private worldService: WorldService, private canvasService: CanvasService, private dialog: MatDialog) {
    this.worldService.world$.subscribe((value: World) => {
      if (this.isActive) {
        this.parseJson(value);
        this.canvasService.drawMap(this.world);
      }
    })
  }

  ngOnInit(): void {
    this.canvasService.initCanvas(this.canvas, this.ctx, this.worldMapContainer);
  }

  parseJson(value: any) {
    this.world.sizeWidth = JSON.parse(value).sizeWidth;
    this.world.sizeHeight = JSON.parse(value).sizeHeight;

    this.world.units = JSON.parse(value).units;
    this.world.buildings = JSON.parse(value).buildings;
    this.world.structures = JSON.parse(value).structures;
  }

  generateModal() {
    this.worldService.getWorldParameters().subscribe(response => {
      this.worldParameters = response;

      const dialogRef = this.dialog.open(ModalGenerate, {
        width: '768px',
        data: this.worldParameters
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result != null) {
          this.wasFirstGenerated = true;
          this.isGenerated = false;

          let worldParameters: WorldParameters = JSON.parse(result.worldParameters);

          this.worldService.generate(worldParameters).subscribe(response => {
            if (response.status === 201) {
              this.isActive = true;
              this.isGenerated = true;
              this.wasFirstGenerated = true;
              this.resizeMap();
            }

            // #todo some error message
          })
        }
      })
    })
  }

  resizeMap() {
    this.canvasService.resize(this.world.sizeHeight);
  }

  pause() {
    this.isActive = !this.isActive;
    this.worldService.pause().subscribe(() => {

    });
  }

  look() {
    this.canvasService.drawMap(this.world);

    let posX = Math.floor(Math.random() * (this.world.sizeWidth - 1)) + 1;
    let posY = Math.floor(Math.random() * (this.world.sizeHeight - 1)) + 1;

    this.worldService.getPathNodes(posX, posY).subscribe(response => {
      this.canvasService.drawPath(response.path);
      this.canvasService.drawUnits(this.world.units);
    })
  }
}
