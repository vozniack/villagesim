import {Component, Input, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {StatisticsService} from "../../service/statistics/statistics.service";

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
  unitHistory: string[] = [];
  buildingHistory: string[] = [];

  newWoodData: any;
  newRocKData: any;
  newFoodData: any;
  newUnitData: any;
  newBuildingData: any;

  dataCounter = 0;

  fullView: boolean = false;

  constructor(private statisticsService: StatisticsService) {
    this.statisticsService.statistics$.subscribe((value: any) => {
      this.parseJson(value);
    })
  }

  ngOnInit(): void {
  }

  reloadView() {
    this.fullView = !this.fullView;
  }

  private parseJson(value: any) {
    this.dataCounter++;

    this.woodHistory.push(JSON.parse(value).wood);
    this.rockHistory.push(JSON.parse(value).rock);
    this.foodHistory.push(JSON.parse(value).food);
    this.unitHistory.push(JSON.parse(value).units);
    this.buildingHistory.push(JSON.parse(value).buildings)

    this.newWoodData = JSON.parse(value).wood;
    this.newRocKData = JSON.parse(value).rock;
    this.newFoodData = JSON.parse(value).food;
    this.newUnitData = JSON.parse(value).units;
    this.newBuildingData = JSON.parse(value).buildings;
  }

}
