import { Token } from '@angular/compiler/src/ml_parser/lexer';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OtpauthenticationService } from '../services/otpauthentication.service';
import { TokenService } from '../services/token.service';
import { DialogData } from '../services/userprofile.service';

@Component({
  selector: 'app-otp-authentication',
  templateUrl: './otp-authentication.component.html',
  styleUrls: ['./otp-authentication.component.css']
})
export class OtpAuthenticationComponent implements OnInit 
{

  errMsg: string;

  editForm= new FormGroup
  (
    {
      OTPValue: new FormControl('', Validators.required)
    }
  )
   
  constructor(public otpRef: MatDialogRef<OtpAuthenticationComponent>, private otpService: OtpauthenticationService,@Inject(MAT_DIALOG_DATA) public data: DialogData, private tokenService: TokenService) { }

  ngOnInit(): void {
  }

  onSubmit()
  { 
    let otpNumber = Number(this.editForm.get("OTPValue").value);
    this.otpService.validateOTP(otpNumber).subscribe(response=>
      { 
          this.otpRef.close(response);
      },
      error => 
      {
        console.log("Error while sending OTP", error);
        this.errMsg = "Otp validation failed";
        // this.otpRef.close('Entered Otp is NOT valid. Please Retry!');
      }
      );
  }

}
