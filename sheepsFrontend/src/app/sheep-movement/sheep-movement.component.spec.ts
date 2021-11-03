import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SheepMovementComponent } from './sheep-movement.component';

describe('SheepMovementComponent', () => {
  let component: SheepMovementComponent;
  let fixture: ComponentFixture<SheepMovementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SheepMovementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SheepMovementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
