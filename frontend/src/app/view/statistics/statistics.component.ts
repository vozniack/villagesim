import {Component, Input, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {ResourceService} from "../../service/resource/resource.service";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0.25}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class StatisticsComponent implements OnInit {

  @Input()
  currentView: string = '';

  woodHistory: string[] = [];
  rockHistory: string[] = [];
  foodHistory: string[] = [];

  newWoodData: any;
  newRocKData: any;
  newFoodData: any;

  constructor(private resourceService: ResourceService) {
    this.resourceService.resource$.subscribe((value: any) => {
      this.parseJson(value);
    })
  }

  ngOnInit(): void {
  }

  private parseJson(value: any) {
    switch (JSON.parse(value).resourceType) {
      case 'WOOD':
        this.woodHistory.push(JSON.parse(value).resourceAmount);
        this.newWoodData = JSON.parse(value).resourceAmount;
        break;

      case 'ROCK':
        this.rockHistory.push(JSON.parse(value).resourceAmount);
        this.newRocKData = JSON.parse(value).resourceAmount;
        break;

      case 'FOOD':
        this.foodHistory.push(JSON.parse(value).resourceAmount);
        this.newFoodData = JSON.parse(value).resourceAmount;
        break;
    }
  }

}
