import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import lodash from 'lodash';
import { environment } from 'src/environments/environment';
import { TokenService } from '../services/token.service';
import { Router } from '@angular/router';
import { UserprofileService } from '../services/userprofile.service';
import { UserProfile } from '../model/user-profile';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  openNotificationValue: boolean;
  showNotification:boolean;  
  sideMenubar: any;
  sideMenuArray = [];
  content = environment.content;
  userName: string;
  userprofile: UserProfile;

  constructor(private authService: AuthenticationService, private tokenService: TokenService, private router: Router, private userprofileService: UserprofileService) { }

  ngOnInit(): void {
    this.getUserAction();
    this.userprofileService.getProfile().subscribe(response=>
    {
        this.userprofile = response;
        if(this.userprofile.firstName!=null && this.userprofile.lastName!=null)
        this.userName =   this.userprofile.firstName + " " + this.userprofile.lastName;
        else this.userName =  this.tokenService.getUser().split("@")[0];
        this.showNotification = this.userprofile.showNotification;
    });
  }

  getUserAction() {
    this.authService.getAction().subscribe(response => {
      const name = response.map(value => value.name);
      this.sideMenubar = this.content.filter(value => {
        const commonValue = lodash.intersection(value.action, name);
        if (commonValue.length >= 1) {
          if (this.sideMenuArray.indexOf(value) === -1) {
            this.sideMenuArray.push(value);
          }
          return value;
        }
      });
    },
    error => {
      console.log("error in action call: ", error);
    })
  }

  
  openNotification(state: boolean) 
  {
    this.openNotificationValue = state;
    if(this.showNotification===true)
    { 
      if(this.openNotificationValue===false)
      { 
        this.showNotification = false;
        this.userprofile.showNotification = this.showNotification;
        this.userprofileService.setProfile(this.userprofile).subscribe(response=>{});
      }
    }
  }

  logout() {
    this.tokenService.signOut();
    this.router.navigate(['/']);
  }

}
