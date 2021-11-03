import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SheepDiseaseMedicineComponent } from './sheep-disease-medicine.component';

describe('SheepDiseaseMedicineComponent', () => {
  let component: SheepDiseaseMedicineComponent;
  let fixture: ComponentFixture<SheepDiseaseMedicineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SheepDiseaseMedicineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SheepDiseaseMedicineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
