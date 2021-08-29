import { TestBed } from '@angular/core/testing';

import { PowerOfAttorneyService } from './power-of-attorney.service';

describe('PowerOfAttorneyService', () => {
  let service: PowerOfAttorneyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PowerOfAttorneyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
