import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewSheepComponent } from './add-new-sheep.component';

describe('AddNewSheepComponent', () => {
  let component: AddNewSheepComponent;
  let fixture: ComponentFixture<AddNewSheepComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewSheepComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewSheepComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
