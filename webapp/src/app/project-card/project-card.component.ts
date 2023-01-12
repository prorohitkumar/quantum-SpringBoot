import { formatDate } from '@angular/common';
import { ElementRef, Input, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Expertise, Project, ProjectStatus, Skill } from '../model/project';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.css']
})
export class ProjectCardComponent implements OnInit {

  @Input() public project: Project;
  @ViewChild('project-description') el: ElementRef;

  description_data: Boolean = false;

  expertise: Expertise;
  skills: Skill;
  skillsString: String;
  isManager:Boolean;


  // expertise: Expertise;
  // skills: Skill;

  constructor(private router: Router, private tokenService: TokenService ) { }

  ngOnInit(): void {

    //this.overflown();
    if(this.tokenService.getRole()=="MANAGER"){
      this.isManager=true;
    }
    else{
      this.isManager=false;
    }

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

    // let endDate = new Date(this.project.startDate);
    // endDate.setDate(endDate.getDate() + (this.project.durationInWeeks * 7));
    // this.project.endDate = endDate;
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
    if(this.project.projectStatus==ProjectStatus.ONGOING){
      return "Ongoing";
    }
   else if(this.project.projectStatus==ProjectStatus.NOT_STARTED){
      return "Not Started";
    }
   else if(this.project.projectStatus==ProjectStatus.COMPLETED){
      return "Completed";
    }
  }

  createTeam(value) {
    this.router.navigate(["/dashboard/createteam", value]);
  }

  viewProject(projectId) {
    this.router.navigate(["/dashboard/projects", projectId]);
  }

  skillString() 
  {
     let skillsString = new Array<String>();
       console.log(this.expertise);
       for(let exps of this.project.expertise)
       {
         for (let skills of exps.skills) 
         {
          if (skillsString.indexOf(skills.skillName)<0)
           {
             skillsString.push(skills.skillName)
           }
         }  
       }

       return skillsString;
  }

}
