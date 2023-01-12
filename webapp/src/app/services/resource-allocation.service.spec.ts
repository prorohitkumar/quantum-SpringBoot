import { TestBed } from '@angular/core/testing';

import { ResourceAllocationService } from './resource-allocation.service';

describe('ResourceAllocationService', () => {
  let service: ResourceAllocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResourceAllocationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
