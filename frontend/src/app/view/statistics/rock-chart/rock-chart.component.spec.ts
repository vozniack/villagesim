import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {RockChartComponent} from './rock-chart.component';

describe('RockChartComponent', () => {
  let component: RockChartComponent;
  let fixture: ComponentFixture<RockChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RockChartComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RockChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
