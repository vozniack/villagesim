import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {WoodChartComponent} from './wood-chart.component';

describe('WoodChartComponent', () => {
  let component: WoodChartComponent;
  let fixture: ComponentFixture<WoodChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [WoodChartComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WoodChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
