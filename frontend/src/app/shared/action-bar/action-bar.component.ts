import {Component, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-action-bar',
  templateUrl: './action-bar.component.html',
  styleUrls: ['./action-bar.component.sass'],
  animations: [
    trigger('showContentAnimated', [
      transition(':enter', [
        style({opacity: 0.5}),
        animate('0.2s linear', style({opacity: 1}))
      ]),
    ]),
  ]
})
export class ActionBarComponent implements OnInit {

  actions: any[] = [
    {'icon': 'settings', 'tooltip': 'Ustawienia', 'active': false},
    {
      'icon': 'house', 'tooltip': 'Budynki', 'active': false, 'children': [
        {'icon': 'home', 'tooltip': 'Dom'}
      ]
    },
    {'icon': 'nature_people', 'tooltip': 'Jednostki', 'active': false},
    {'icon': 'book', 'tooltip': 'Polecenia', 'active': false}
  ];

  constructor() {
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

}
