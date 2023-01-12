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
export class SearchService {

  URL = environment.BASE_URL+"searchservice/api/v1/"

  tokenHeader: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenService.getToken()}`
  });

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  forSearch(keyWord: string) {

  //   console.log(searchObj);
  //   //this.URL = this.URL + '/' + searchObj;    
  //  // this.URL = this.URL + '/' + searchObj.Value;
  //   return this.httpClient.get<Project[]>(this.URL);
  // }
  return this.http.get<any>(this.URL+"projects/search", {
    headers: this.tokenHeader,
    params: {
      value: keyWord
    }
  }).pipe(catchError(this.errorHandler));
}

forProject(keyWord:string){
  return this.http.get<any>(this.URL+"projects/search", {
    headers: this.tokenHeader,
    params: {
      value: keyWord
    }
  }).pipe(catchError(this.errorHandler));


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
