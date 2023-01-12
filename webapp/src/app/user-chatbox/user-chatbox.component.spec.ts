import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserChatboxComponent } from './user-chatbox.component';

describe('UserChatboxComponent', () => {
  let component: UserChatboxComponent;
  let fixture: ComponentFixture<UserChatboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserChatboxComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserChatboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
