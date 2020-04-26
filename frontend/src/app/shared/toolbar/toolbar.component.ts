import {Component, OnInit} from '@angular/core';
import {StatusService} from "../../service/status/status.service";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
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
