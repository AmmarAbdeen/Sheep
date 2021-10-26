import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlacesFeedComponent } from './places-feed.component';

describe('PlacesFeedComponent', () => {
  let component: PlacesFeedComponent;
  let fixture: ComponentFixture<PlacesFeedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlacesFeedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlacesFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
