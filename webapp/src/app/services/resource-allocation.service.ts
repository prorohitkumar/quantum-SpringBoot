import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Project } from '../model/project';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ResourceAllocationService {

  URL = environment.BASE_URL+"resourceallocationengine/api/v1/";

  tokenHeader: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenService.getToken()}`
  });

  constructor(private httpClient: HttpClient, private tokenService: TokenService) { }

  getSuggestedUsers(project: Project) {
    return this.httpClient.post(this.URL+"userSuggestions", project, {headers: this.tokenHeader,}).pipe(catchError(this.errorHandler));
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
