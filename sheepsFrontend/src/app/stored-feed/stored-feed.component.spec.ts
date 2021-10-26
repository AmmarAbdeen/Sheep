import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StoredFeedComponent } from './stored-feed.component';

describe('StoredFeedComponent', () => {
  let component: StoredFeedComponent;
  let fixture: ComponentFixture<StoredFeedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StoredFeedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StoredFeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
