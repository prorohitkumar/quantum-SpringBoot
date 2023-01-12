
import { formatDate } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectStatus } from '../model/project';
import { ProjectService } from '../services/project.service';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-project-view',
  templateUrl: './project-view.component.html',
  styleUrls: ['./project-view.component.css']
})
export class ProjectViewComponent implements OnInit {

  constructor(private projectService : ProjectService, private activateRoute: ActivatedRoute, private router : Router, private tokenService: TokenService ) { }
  
  projectId : string;

  project: any;
  isProjectLoaded : boolean = false;
  isTeamFormed: boolean = true;
  startDate : any;
  endDate : any;
  statusIcon : any;
  statusColor : any;

  panelOpenState = false;
  isManager:Boolean;

  projectImage = "../assets/images/project.jpg";
  resourceImage = "../assets/images/resource.jpg";

  ngOnInit(): void {

    if(this.tokenService.getRole()=="MANAGER"){
      this.isManager=true;
    }
    else{
      this.isManager=false;
    }

    this.projectId = this.activateRoute.snapshot.params.projectId;
    this.getProject();
  }

  getProject(){
    this.projectService.getProjectDetail(this.projectId)
    .subscribe(response => {
      console.log(response);
      this.project = response['body'];
      console.log(this.project);
      this.isTeamFormed = this.project.teamCreated; 
      this.setDates();
      this.setStatusIcon();      
      this.isProjectLoaded = true; 
    }),error => {
      console.log(error);
    }
  } 

  setDates(){

    var endDate = new Date(this.project.startDate);
    for (let i = 0; i < this.project.durationInWeeks; i++) {
      endDate.setDate(endDate.getDate() + 7);
    }
    this.project.endDate = new Date(endDate);
    console.log(this.project.endDate.getUTCFullYear());

    if (formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') < formatDate(this.project.startDate, 'yyyy-MM-dd', 'en_US') && formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') < formatDate(this.project.endDate, 'yyyy-MM-dd', 'en_US')) {
      this.project.projectStatus = ProjectStatus.NOT_STARTED;

    }
    else if (formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') >= formatDate(this.project.startDate, 'yyyy-MM-dd', 'en_US') && formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') < formatDate(this.project.endDate, 'yyyy-MM-dd', 'en_US')) {
      this.project.projectStatus = ProjectStatus.ONGOING;
    }
    else if (formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') > formatDate(this.project.startDate, 'yyyy-MM-dd', 'en_US') && formatDate(Date.now(), 'yyyy-MM-dd', 'en_US') >= formatDate(this.project.endDate, 'yyyy-MM-dd', 'en_US')) {
      this.project.projectStatus = ProjectStatus.COMPLETED;
    }
  }

  setStatusIcon(){
    if(this.project.projectStatus == "ONGOING"){
      this.statusIcon = "integration_instructions";
      this.statusColor = "orange";
    }
    else if(this.project.projectStatus == "COMPLETED"){
      this.statusIcon = "check_circle";
      this.statusColor = "green";
    }
    else{
      this.statusIcon = "pending_actions";
      this.statusColor = "red";
    }
  }
   
  getColor(projectStatus) {
    (2)
    switch (projectStatus) {
      case 'ONGOING':
        return 'orange';
      case 'COMPLETED':
        return 'green';
      case 'NOT_STARTED':
        return 'red';
    }
  }
  getProjectStatus(){
    if(this.project.projectStatus ==ProjectStatus.ONGOING){
      return "ONGOING";
    }
    else if(this.project.projectStatus ==ProjectStatus.NOT_STARTED){
      return "NOT STARTED";
    }
    else if(this.project.projectStatus==ProjectStatus.COMPLETED){
      return "COMPLETED";
    }
  }

  createTeam(){
    this.router.navigate(['/dashboard/createteam/' + this.projectId]);
  }

  /**For Testing purposes **/

  // getProject(){
  //   this.httpClient.get('http://localhost:3000/projects')
  //   .subscribe(response => {
  //     console.log(response);
  //     this.project = response[0];
  //     console.log(this.project);
  //     this.isProjectLoaded = true; 
  //     this.isTeamFormed = this.project.teamCreated; 
  //     this.setDates();
  //     this.setStatusIcon();
  //   }),error => {
  //     console.log(error);
  //   }
  // }
    



}
