import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-unit-chart',
  templateUrl: './unit-chart.component.html',
  styleUrls: ['./unit-chart.component.sass']
})
export class UnitChartComponent implements OnInit {

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
          areaStyle: {color: 'rgba(0, 188, 212, 0.75)'},
          itemStyle: {color: 'rgba(0, 188, 212, 0.75)'},
        }
      ],
      animationEasing: 'elasticIn',
    };
  }

}
