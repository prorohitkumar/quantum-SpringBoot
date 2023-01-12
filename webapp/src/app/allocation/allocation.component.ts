import { FixedSizeVirtualScrollStrategy } from '@angular/cdk/scrolling';
import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Expertise1 } from '../model/expertise1';
import { Expertise, Project, ProjectStatus } from '../model/project';
import { Resource } from '../model/resource';
import { UserProfile } from '../model/user-profile';
import { ProjectService } from '../services/project.service';
import { ResourceAllocationService } from '../services/resource-allocation.service';
import { UserprofileService } from '../services/userprofile.service';
import { TeamDialogComponent } from '../team-dialog/team-dialog.component';

@Component({
  selector: 'app-allocation',
  templateUrl: './allocation.component.html',
  styleUrls: ['./allocation.component.css']
})
export class AllocationComponent implements OnInit {

  expertiseArray: any;
  expertiseArray1: Expertise1[] = [];
  allocatedResources: Array<Resource> = new Array<Resource>();
  resourcesConfirmed: Array<UserProfile>;
  project: Project;
  recommendedResources: any;
  projectId: string;
  indexOfExpertise: any;
  indexOfResource: any;
  expertises: Array<Expertise> = new Array;
  resourcesSelected: Array<Resource> = new Array;
  panelOpenState = false;
  isProjectLoaded: boolean = false;
  isResourcesLoaded: boolean = false;
  isError: boolean = false;
  startDate: any;
  endDate: any;
  statusIcon: any;
  statusColor: any;
  backgroundImage = "../assets/images/629567.jpg";
  count: any;
  count2: any;
  count3: any;

  constructor(private httpClient: HttpClient, private userProfileService: UserprofileService, public dialog: MatDialog,
    private projectService: ProjectService, private resourceAllocationService: ResourceAllocationService, private activateRoute: ActivatedRoute) { }



  ngOnInit(): void {
    this.projectId = this.activateRoute.snapshot.params.projectId;
    this.getProject();
  }

  getProject() {
    this.projectService.getProjectDetail(this.projectId)
      .subscribe(response => {
        this.project = response['body'];
        console.log(this.project);
        this.setDates();
        this.setStatusIcon();
        this.isProjectLoaded = true;
        this.getResources();
      }), error => {
        console.log(error);
      }
  }

  setDates() {
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

  getResources() {
    this.resourceAllocationService.getSuggestedUsers(this.project)
      .subscribe(response => {
        console.log(response);
        this.expertiseArray = response;
        console.log(this.expertiseArray);
        this.isResourcesLoaded = true;
      }, error => {
        console.log(error);
        this.isError = true;
      });
  }

  setStatusIcon() {
    if (this.project.projectStatus == "ONGOING") {
      this.statusIcon = "integration_instructions";
      this.statusColor = "orange";
    }
    else if (this.project.projectStatus == "COMPLETED") {
      this.statusIcon = "check_circle";
      this.statusColor = "green";
    }
    else {
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

  getIndex(value: any) {
    this.deleteDev(value.expertiseIndex, value.resourceIndex);
  }


  deleteDev(i, j) {
    this.expertiseArray[i].resourcesSuggested.splice(j, 1);
    this.count3 = this.expertiseArray[i].resourcesSuggested.length - this.expertiseArray[i].noOfResources / 3;
    console.log(this.count3);

  }

  onSubmit() {

    this.allocatedResources = new Array<Resource>()
    for (var i = 0; i < this.expertiseArray.length; i++) {
      for (var j = 0; j < (this.expertiseArray[i].noOfResources) / 3; j++) {
        this.allocatedResources.push(this.expertiseArray[i].resourcesSuggested[j])
      }
    }
    console.log(this.allocatedResources);
    this.resourcesConfirmed = new Array;
    for (var i = 0; i < this.allocatedResources.length; i++) {
      var email = this.allocatedResources[i].email;
      console.log(email);
      this.userProfileService.getProfileById(email)
        .subscribe(response => {
          var userProfile = response;
          console.log(userProfile);
          this.resourcesConfirmed.push(userProfile);
        }), error => {
          console.log(error);
        }
    }
    const dialogRef = this.dialog.open(TeamDialogComponent, {
      width: '100%',
      data: {
        project: this.project,
        resources: this.resourcesConfirmed
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });

  }

  loadingResourcesSpinner() {
    if (this.isResourcesLoaded) {
      return false;
    }
    else if (this.isError) {
      return false;
    }
    else {
      return true;
    }
  }

  tryAgain() {
    console.log('finding again');
    this.isError = false;
    this.getResources();
  }
}
