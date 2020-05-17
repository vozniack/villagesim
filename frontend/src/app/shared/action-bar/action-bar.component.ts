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
        {'icon': 'apartment', 'name': 'Szkoła', 'actionType': 'BUILDING_SCHOOL'},
        {'icon': 'free_breakfast', 'name': 'Gospoda', 'actionType': 'BUILDING_INN'},
        {'icon': 'spa', 'name': 'Farma', 'actionType': 'BUILDING_FARM'}
      ]
    },
    {
      'icon': 'nature_people', 'name': 'Jednostki', 'active': false, 'children': [
        {'icon': 'person', 'name': 'Pomocnik', 'actionType': 'UNIT_PEASANT'}
      ]
    },
    {
      'icon': 'book', 'name': 'Polecenia', 'active': false, 'children': [
        {'icon': 'fireplace', 'name': 'Zetnij drzewo', 'actionType': 'ACTION_CUT_TREE'},
        {'icon': 'sports_cricket', 'name': 'Rozbij kamień', 'actionType': 'ACTION_BREAK_STONE'}
      ]
    },
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
