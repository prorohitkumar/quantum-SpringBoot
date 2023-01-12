import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogPageComponent } from '../dialog-page/dialog-page.component';
import { Project } from '../model/project';
import { ProjectService } from '../services/project.service';

@Component({
  selector: 'app-team-dialog',
  templateUrl: './team-dialog.component.html',
  styleUrls: ['./team-dialog.component.css']
})
export class TeamDialogComponent implements OnInit {

  isCreating : boolean = false;

  constructor(public dialogRef: MatDialogRef<TeamDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private projectService : ProjectService, private router: Router, public dialog:MatDialog) { }

  ngOnInit(): void {
  }

  confirm(){
    this.isCreating = true;
    var project : Project = this.data.project;
    project.allocatedResources = this.data.resources;
    project.teamCreated = true;
    console.log(project);
    this.projectService.updateProject(project.projectId, project)
      .subscribe(response => {
        console.log(response);
        this.dialogRef.close();
        this.openSuccessDialog(project);        
      }), error => {
        this.isCreating = false;
        console.log(error);
        this.openErrorDialog(error.error);
      }
    
  }

  openSuccessDialog(project): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: "Team Successfully Created!"}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
    
    this.router.navigate(['/dashboard/projects/' + project.projectId]);    
  }

  openErrorDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: messageData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  close(){
    this.dialogRef.close();
  }
  
  isCreatingTeam(){
    if(this.isCreating){
      return true;
    }
    else{
      return false;
    }
  }

}
