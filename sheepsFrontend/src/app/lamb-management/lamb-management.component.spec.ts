import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LambManagementComponent } from './lamb-management.component';

describe('LambManagementComponent', () => {
  let component: LambManagementComponent;
  let fixture: ComponentFixture<LambManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LambManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LambManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
