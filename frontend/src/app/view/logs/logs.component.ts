import {Component, Input, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {LogService} from "../../service/log/log.service";
import {Log} from "../../model/others/log";

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0.25}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class LogsComponent implements OnInit {

  @Input()
  currentView: string = '';

  logs: Log[] = [];

  constructor(private logService: LogService) {
    this.logService.log$.subscribe((value: any) => {
      this.parseJson(value);
    })
  }

  ngOnInit(): void {
  }

  parseJson(value: any) {
    let newLog = new Log();

    newLog.message = JSON.parse(value).message;
    newLog.time = JSON.parse(value).time;
    newLog.status = JSON.parse(value).status;

    this.logs.unshift(newLog);
  }

}
