import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewLambComponent } from './add-new-lamb.component';

describe('AddNewLambComponent', () => {
  let component: AddNewLambComponent;
  let fixture: ComponentFixture<AddNewLambComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewLambComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewLambComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
