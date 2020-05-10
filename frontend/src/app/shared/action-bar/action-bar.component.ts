import {Component, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";
import {BuildingService} from "../../service/building/building.service";
import {UnitService} from "../../service/unit/unit.service";

@Component({
  selector: 'app-action-bar',
  templateUrl: './action-bar.component.html',
  styleUrls: ['./action-bar.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class ActionBarComponent implements OnInit {

  actions: any[] = [
    {
      'icon': 'home', 'name': 'Budynki', 'active': false, 'children': [
        {'icon': 'house', 'name': 'Dom', 'actionType': 'BUILDING_HOUSE'},
        {'icon': 'house', 'name': 'Szkoła', 'actionType': 'BUILDING_SCHOOL'},
        {'icon': 'house', 'name': 'Gospoda', 'actionType': 'BUILDING_INN'}
      ]
    },
    {
      'icon': 'nature_people', 'name': 'Jednostki', 'active': false, 'children': [
        {'icon': 'person', 'name': 'Pomocnik', 'actionType': 'UNIT_PEASANT'},
        {'icon': 'person', 'name': 'Robotnik', 'actionType': 'UNIT_WORKER'}
      ]
    },
    {'icon': 'book', 'name': 'Polecenia', 'active': false}
  ];

  constructor(private buildingService: BuildingService, private unitService: UnitService) {
  }

  ngOnInit(): void {
  }

  changeActionActive(action: any) {
    if (!action.active) {
      this.actions.forEach(action => action.active = false);
      action.active = true;
    } else {
      action.active = false;
    }
  }

  doAction(child: any) {
    let actionProperty = child.actionType.split("_")[1];

    switch (child.actionType.split("_")[0]) {
      case 'BUILDING':
        this.buildingService.createBuilding(actionProperty).subscribe(response => {
          this.parseResponse(response);
        });

        break;

      case 'UNIT':
        this.unitService.createUnit(actionProperty).subscribe(response => {
          this.parseResponse(response);
        });

        break;
    }

    this.changeActionActive(child);
  }

  parseResponse(response: any) {
    console.log(response.status);
  }

}
