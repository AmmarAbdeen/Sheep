import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SheepManagementComponent } from './sheep-management.component';

describe('SheepManagementComponent', () => {
  let component: SheepManagementComponent;
  let fixture: ComponentFixture<SheepManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SheepManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SheepManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
