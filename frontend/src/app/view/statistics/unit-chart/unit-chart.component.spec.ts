import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UnitChartComponent} from './unit-chart.component';

describe('UnitChartComponent', () => {
  let component: UnitChartComponent;
  let fixture: ComponentFixture<UnitChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UnitChartComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
