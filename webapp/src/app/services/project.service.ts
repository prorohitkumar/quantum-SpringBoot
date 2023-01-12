import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Project } from '../model/project';
import { environment } from 'src/environments/environment';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  tokenHeader: HttpHeaders = new HttpHeaders({
    'Authorization': `Bearer ${this.tokenService.getToken()}`
  });

  URL = environment.BASE_URL+"projectservice/api/v1/";
  constructor(private httpClient: HttpClient,private tokenService: TokenService) { }

  getProjectDetails()
  {
    return this.httpClient.get<Project[]>(this.URL+'projects', {
      headers: this.tokenHeader,
      params: {
       managerEmail:this.tokenService.getUser()
        // managerEmail:this.tokenService.getUser()
        //managerEmail:"venkat@tsymphony.in"
      }
    }).pipe(catchError(this.errorHandler));
  }

  getResourceProjectDetails()
  {
    return this.httpClient.get<Project[]>(this.URL+'projects', {headers: this.tokenHeader}).pipe(catchError(this.errorHandler));
  }


  getProjectDetail(projectId){
    return this.httpClient.get<Project>(this.URL+'project', {headers: this.tokenHeader,params: {["projectId"] : projectId}, observe: "response"})
      .pipe(catchError(this.errorHandler));
  }

  postProjectDetails(project: Project)
  {
    console.log(project);
    return this.httpClient.post(this.URL+'project', project, {headers: this.tokenHeader}).pipe(catchError(this.errorHandler));
  }

  updateProject(projectId, project : Project){
    return this.httpClient.put(this.URL + "project/" + projectId, project, {headers: this.tokenHeader,}).pipe(catchError(this.errorHandler));
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
