import {Component, Input, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";

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

  constructor() {
  }

  ngOnInit(): void {
  }

}
