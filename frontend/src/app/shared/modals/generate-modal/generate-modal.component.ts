import { Component, OnInit } from '@angular/core';
import {WorldParameters} from "../../../model/world/worldParameters";

@Component({
  selector: 'app-generate-modal',
  templateUrl: './generate-modal.component.html',
  styleUrls: ['./generate-modal.component.sass']
})
export class GenerateModalComponent implements OnInit {

  worldParameters: WorldParameters;

  constructor() { }

  ngOnInit(): void {
  }

}
