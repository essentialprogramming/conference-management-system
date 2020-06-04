import { TestBed, inject } from '@angular/core/testing';

import { ChairGuardService } from './chair-guard.service';

describe('ChairGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChairGuardService]
    });
  });

  it('should be created', inject([ChairGuardService], (service: ChairGuardService) => {
    expect(service).toBeTruthy();
  }));
});
