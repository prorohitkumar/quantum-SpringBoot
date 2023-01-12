import { TestBed } from '@angular/core/testing';

import { OtpauthenticationService } from './otpauthentication.service';

describe('OtpauthenticationService', () => {
  let service: OtpauthenticationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OtpauthenticationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
