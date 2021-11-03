import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SheepMovementSearchComponent } from './sheep-movement-search.component';

describe('SheepMovementSearchComponent', () => {
  let component: SheepMovementSearchComponent;
  let fixture: ComponentFixture<SheepMovementSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SheepMovementSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SheepMovementSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
