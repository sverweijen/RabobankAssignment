import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PowerOfAttorneyComponent } from './power-of-attorney.component';

describe('PowerOfAttorneyComponent', () => {
  let component: PowerOfAttorneyComponent;
  let fixture: ComponentFixture<PowerOfAttorneyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PowerOfAttorneyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PowerOfAttorneyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
