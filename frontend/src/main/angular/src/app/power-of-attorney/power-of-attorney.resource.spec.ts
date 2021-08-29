import { TestBed } from '@angular/core/testing';

import { PowerOfAttorneyResource } from './power-of-attorney.resource';

describe('PowerOfAttorneyService', () => {
  let service: PowerOfAttorneyResource;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PowerOfAttorneyResource);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
