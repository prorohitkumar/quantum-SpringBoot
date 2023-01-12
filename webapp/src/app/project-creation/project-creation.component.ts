import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Expertise, Manager, Project, ProjectStatus } from '../model/project';
import { UserProfile } from '../model/user-profile';
import { DialogFlowService } from '../services/dialog-flow.service';
import { ProjectService } from '../services/project.service';
import { TokenService } from '../services/token.service';
import { UserprofileService } from '../services/userprofile.service';

@Component({
  selector: 'app-project-creation',
  templateUrl: './project-creation.component.html',
  styleUrls: ['./project-creation.component.css']
})
export class ProjectCreationComponent implements OnInit {

  message: string;
  userProfile: UserProfile = new UserProfile();
  project: Project = new Project();
  question: string;
  dataArray: Array<string> = new Array<string>();
  

  submitForm= new FormGroup
  (
    {
      reply: new FormControl('', Validators.required)
    }
  )

  constructor(private dialogFlowService: DialogFlowService, private userprofileService: UserprofileService, private projectService: ProjectService, private tokenService: TokenService) { }

  ngOnInit(): void 
  {
     this.getUserdata();
     this.initializeProject();
     this.dialogFlowService.getDialogFlow('create a project').subscribe(response=>
      {
        this.question=response;

      });
  }

  getUserdata()
  {
    this.userprofileService.getProfile().subscribe(response=>
    {
      this.userProfile = response;
      this.initializeProject();
    });
  }

  initializeProject()
  {
    this.project.manager = new Manager();
    this.project.manager.firstName = String(this.userProfile.firstName);
    this.project.manager.lastName = String(this.userProfile.lastName);
    this.project.manager.email = this.tokenService.getUser();
    this.project.projectStatus=ProjectStatus.NOT_STARTED;
    this.project.expertise = new Array<Expertise>();

  }

  onSubmit()
  {
    this.dialogFlowService.getDialogFlow(this.submitForm.get('reply').value).subscribe(response=>
      {
        this.dataArray.push(response);
        if(this.dataArray.length<=4)
        {this.dialogFlowService.getDialogFlow('next').subscribe(response=>
          {   this.submitForm.get('reply').setValue('');
              this.question = response;
          });
        }
        else
        {
          this.question = 'Initial Details Stored';
          this.project.projectName= String(this.dataArray[0]);
          this.project.projectDescription = String(this.dataArray[1]);
          this.project.location = String(this.dataArray[2]);
          this.project.durationInWeeks = this.dataArray[3]
          // this.project.startDate = new Date(this.dataArray[4]);

          this.projectService.postProjectDetails(this.project).subscribe(response=>
            {
                  console.log(response); 
            });
        }
    });
  }
}

