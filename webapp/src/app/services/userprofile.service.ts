import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../model/user';
import { UserProfile } from '../model/user-profile';
import { catchError } from "rxjs/operators";
import { throwError } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class UserprofileService {

  URL = environment.BASE_URL+"profileservice/api/v1/userprofile/";

  tokenHeader: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenService.getToken()}`
  });

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  getProfile()
  {
    return this.httpClient.get<UserProfile>(this.URL+"fetchuser", {
      headers: this.tokenHeader,
      params: {
        email: this.tokenService.getUser()
      }
    }).pipe(catchError(this.errorHandler));
  }

  getProfileById(email){
    return this.httpClient.get<UserProfile>(this.URL + "fetchuser" , {
      headers: this.tokenHeader,
      params:{
        email : email
      }
    }).pipe(catchError(this.errorHandler));
  }

  setProfile(userProfile: UserProfile)
  {
    return this.httpClient.put<UserProfile>(this.URL+"update", userProfile, {
      headers: this.tokenHeader,
      params: {
        email: this.tokenService.getUser()
      }
    }).pipe(catchError(this.errorHandler));
  }

  createUser(user: User) {
    return this.httpClient.post(this.URL+"register", user).pipe(catchError(this.errorHandler));
  }

  onUpload(uploadImageData:any){
    return this.httpClient.put(this.URL+"upload", uploadImageData, { headers: { responseType: 'text' }});
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

export interface DialogData
{
   messageData: String;
}
