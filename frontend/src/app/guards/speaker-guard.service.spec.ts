import { TestBed, inject } from '@angular/core/testing';

import { SpeakerGuardService } from './speaker-guard.service';

describe('SpeakerGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpeakerGuardService]
    });
  });

  it('should be created', inject([SpeakerGuardService], (service: SpeakerGuardService) => {
    expect(service).toBeTruthy();
  }));
});
