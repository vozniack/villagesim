import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ModalGenerate} from './modal-generate.component';

describe('GenerateModalComponent', () => {
  let component: ModalGenerate;
  let fixture: ComponentFixture<ModalGenerate>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ModalGenerate]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalGenerate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
