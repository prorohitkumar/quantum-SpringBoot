import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Resource } from '../model/resource';
import { environment } from 'src/environments/environment';
import { catchError } from "rxjs/operators";
import { Project } from '../model/project';


@Injectable({
  providedIn: 'root'
})
export class ProfileCardServiceService {

  URL = "http://localhost:3000/profileCard";
  URLProject="http://localhost:8000/projects";

  constructor(private httpClient: HttpClient) { }

  
  getProfile()
  {
    return this.httpClient.get<Array<Resource>>(this.URL);
  }

  getProjectDetails(){
    return this.httpClient.get<Array<Project>>(this.URLProject);
  }
}
