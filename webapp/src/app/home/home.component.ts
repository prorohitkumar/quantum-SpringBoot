import { Component, OnInit } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogPageComponent } from '../dialog-page/dialog-page.component';
import {User} from '../model/user';
import { AuthenticationService } from '../services/authentication.service';
import { TokenService } from '../services/token.service';
import { UserprofileService } from '../services/userprofile.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userData: User = new User();
  listOfUsers: Array<User> = [];
 
  loginForm:FormGroup;
  showLogin:boolean;
  signupForm:FormGroup;

  constructor(private authService: AuthenticationService, public router: Router, private token: TokenService, private userService: UserprofileService, public dialog:MatDialog) { }

  ngOnInit(): void {
    this.showLogin=true;
    this.loginForm=new FormGroup(
      {

        email:new FormControl('',[Validators.required,Validators.email]),
        password:new FormControl('',[Validators.required,Validators.maxLength(20),Validators.minLength(5)])

      }
    );

    this.signupForm=new FormGroup(
      {

        email:new FormControl('',[Validators.required,Validators.email]),
        password:new FormControl('',[Validators.required,Validators.maxLength(20),Validators.minLength(5)])

      }
    ); 
  }

  openLoginDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: messageData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.router.navigate(['/dashboard']);
    });
  }

  openRegisterDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: messageData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  showRegister(){
    this.showLogin=false;
    this.loginForm.reset();
  }

  showLoginForm(){
    this.showLogin=true;
    this.signupForm.reset();
  }

  

  submitLogin(){
    this.userData.email = this.loginForm.value.email;
    this.userData.password = this.loginForm.value.password;
    this.authService.loginUser(this.userData)
              .subscribe(res => {
                this.token.saveToken(res['token']);
                this.token.saveUser(this.userData.email);
                this.token.saveRole(res['role']);
                this.loginForm.reset();
                //this.openLoginDialog(" Login successful ");
                this.router.navigate(['/dashboard']);
              }
              , error => {
                console.log("error while login: ", error);
                this.openRegisterDialog(error.error);
              })
  }

  submitRegister(){
    console.log("signupForm",this.signupForm.value);
    this.userData.email = this.signupForm.value.email;
    this.userData.password = this.signupForm.value.password;
    this.userService.createUser(this.userData)
              .subscribe(res => {
                console.log("Registered Successfully");
                this.signupForm.reset();
                this.openRegisterDialog(" Registration was successful ");
              }, error => {
                console.log("error in registering", error);
                this.openRegisterDialog(error.error);
              });
  }

}
