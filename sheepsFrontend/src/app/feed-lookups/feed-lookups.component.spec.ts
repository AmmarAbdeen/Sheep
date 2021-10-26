import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedLookupsComponent } from './feed-lookups.component';

describe('FeedLookupsComponent', () => {
  let component: FeedLookupsComponent;
  let fixture: ComponentFixture<FeedLookupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedLookupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedLookupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
