import { TestBed } from '@angular/core/testing';

import { ProfileCardServiceService } from './profile-card-service.service';

describe('ProfileCardServiceService', () => {
  let service: ProfileCardServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileCardServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
