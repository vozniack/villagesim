import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-rock-chart',
  templateUrl: './rock-chart.component.html',
  styleUrls: ['./rock-chart.component.sass']
})
export class RockChartComponent implements OnInit {

  options: any;
  data: any[] = [];

  @Input()
  dataHistory: any = [];

  constructor() {
  }

  @Input()
  set newData(newData: any) {
    if (newData != null) {
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
          areaStyle: {color: 'rgba(120, 120, 120, 0.85)'},
          itemStyle: {color: 'rgba(120, 120, 120, 0.85)'},
        }
      ],
      animationEasing: 'elasticIn',
    };
  }


}
