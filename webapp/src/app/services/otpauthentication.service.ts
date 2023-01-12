import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';
import { catchError } from "rxjs/operators";
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class OtpauthenticationService {

  URL = environment.BASE_URL+"authenticationservice/api/v1/";

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  sendOTP()
  {
    return this.httpClient.get(this.URL+"otp/"+this.tokenService.getUser(),{responseType: 'text'})
    //return this.httpClient.get("localhost:8085/api/v1/otp/shivamtomar005@gmail.com",{responseType: 'text'})
    .pipe(catchError(this.errorHandler));
  }
  
  validateOTP(otp: number)
  {
    return this.httpClient.get(this.URL+"validate/"+this.tokenService.getUser()+"/"+otp,{responseType: 'text'})
    //return this.httpClient.get("localhost:8085/api/v1/validate/shivamtomar005@gmail.com"+"/"+otp,{responseType: 'text'})
    .pipe(catchError(this.errorHandler));
  }

  public errorHandler(error: Response|any) {
    if (error instanceof ErrorEvent)
      {console.error("an error occured:",error.message );
    return throwError("something bad happened");
    }
    else{
      console.error( `backend returned code ${error.status},`+
      `body was:${error.message}`);
      return throwError(error);
    }
  }
}
