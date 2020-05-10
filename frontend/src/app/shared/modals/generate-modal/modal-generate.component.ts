import {Component, Inject} from '@angular/core';
import {WorldParameters} from "../../../model/others/worldParameters";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-generate-modal',
  templateUrl: './modal-generate.component.html',
  styleUrls: ['./modal-generate.component.sass']
})
export class ModalGenerate {

  worldParameters: WorldParameters;

  constructor(public dialogRef: MatDialogRef<ModalGenerate>, @Inject(MAT_DIALOG_DATA) public data: WorldParameters) {
    this.worldParameters = data;
  }

  default() {
    this.worldParameters.width = 96;
    this.worldParameters.height = 32;

    this.worldParameters.treeFactor = 0.1;
    this.worldParameters.rockFactor = 0.05;

    this.worldParameters.wood = 64;
    this.worldParameters.rock = 32;
    this.worldParameters.food = 16;
    this.worldParameters.gold = 4;

    this.worldParameters.peasants = 4;
    this.worldParameters.workers = 2;
  }

  save() {
    this.dialogRef.close({worldParameters: JSON.stringify(this.worldParameters)});
  }

}
