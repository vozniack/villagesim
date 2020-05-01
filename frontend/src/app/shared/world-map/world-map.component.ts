import {Component, OnInit} from '@angular/core';
import {WorldService} from "../../service/world/world.service";

@Component({
  selector: 'app-world-map',
  templateUrl: './world-map.component.html',
  styleUrls: ['./world-map.component.sass']
})
export class WorldMapComponent implements OnInit {

  constructor(private worldService: WorldService) {
  }

  ngOnInit(): void {
    this.worldService.getWorld();
  }

  generate() {
    this.worldService.generate().subscribe(response => {
      console.log(response);
    })
  }
}
