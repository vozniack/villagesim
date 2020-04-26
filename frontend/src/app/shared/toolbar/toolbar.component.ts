import {Component, OnInit} from '@angular/core';
import {StatusService} from "../../service/status/status.service";
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0.5}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class ToolbarComponent implements OnInit {
  serverStatus: string = 'Niedostępny'

  constructor(private statusService: StatusService) {
  }

  ngOnInit() {
    this.getServerStatus();
  }

  getServerStatus() {
    this.statusService.getServerStatus().subscribe(response => {
      this.serverStatus = response.status;
    })
  }

}
