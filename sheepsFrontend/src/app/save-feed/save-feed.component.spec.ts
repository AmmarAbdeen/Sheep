import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveFeedComponent } from './save-feed.component';

describe('SaveFeedComponent', () => {
  let component: SaveFeedComponent;
  let fixture: ComponentFixture<SaveFeedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveFeedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
