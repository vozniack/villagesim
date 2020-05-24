import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-food-chart',
  templateUrl: './food-chart.component.html',
  styleUrls: ['./food-chart.component.sass']
})
export class FoodChartComponent implements OnInit {

  options: any;
  data: any[] = [];

  @Input()
  dataHistory: any = [];

  constructor() {
  }

  @Input()
  set dataCounter(dataCounter: any) {
    if (dataCounter != null) {
      this.refreshOptions();
    }
  }

  ngOnInit(): void {
    this.data = this.dataHistory;
    this.refreshOptions();
  }

  refreshOptions() {
    this.options = {
      tooltip: {},
      xAxis: {
        data: [],
        silent: false,
        splitLine: {
          show: false,
        },
      },
      yAxis: {},
      series: [
        {
          data: this.dataHistory,
          type: 'line',
          smooth: true,
          areaStyle: {color: 'rgba(219, 152, 57, 0.85)'},
          itemStyle: {color: 'rgba(219, 152, 57, 0.85)'},
        }
      ],
      animationEasing: 'elasticIn',
    };
  }
}
