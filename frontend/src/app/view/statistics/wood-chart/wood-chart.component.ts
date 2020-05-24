import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-wood-chart',
  templateUrl: './wood-chart.component.html',
  styleUrls: ['./wood-chart.component.sass']
})
export class WoodChartComponent implements OnInit {

  options: any;
  data: any[] = [];

  @Input()
  dataHistory: any = [];

  @Input()
  set dataCounter(dataCounter: any) {
    if (dataCounter != null) {
      this.refreshOptions();
    }
  }

  constructor() {
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
          areaStyle: {color: 'rgba(93, 64, 55, 0.85)'},
          itemStyle: {color: 'rgba(93, 64, 55, 0.85)'},
        }
      ],
      animationEasing: 'elasticIn',
    };

  }
}
