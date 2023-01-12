import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { ProjectCreationComponent } from './project-creation/project-creation.component';
import { ProjectCreatorComponent } from './project-creator/project-creator.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ResourceDashboardComponent } from './resource-dashboard/resource-dashboard.component';
import { ProjectViewComponent } from './project-view/project-view.component';
import { UserprofilePageComponent } from './userprofile-page/userprofile-page.component';
import { AllocationComponent } from './allocation/allocation.component';
import { UserChatboxComponent } from './user-chatbox/user-chatbox.component';
const routes: Routes = [
  {
    path: '',
    component: HomeComponent
    //component:UserprofilePageComponent
  },
  {
    path: 'resource',
    component: ResourceDashboardComponent
  },
  {
    path: 'profile',
    component: UserprofilePageComponent
  },
  {
    path: 'project',
    component: ProjectDashboardComponent
    
  },
  {
    path:'projectcreate',
    component: ProjectCreationComponent
  },
  {
    path:'createproject',
    component: ProjectCreatorComponent
  },
  {
    path:'userchatbox',
    component:UserChatboxComponent
  },
  
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      {
        path: 'user',
        component: UserprofilePageComponent
      },
      {
        path: '',
        component: ProjectDashboardComponent
        
      },
      {
        path:'createproject',
        component: ProjectCreatorComponent
      },
      {
        path: 'projects/:projectId',
        component: ProjectViewComponent
      },
     {
      path:'createteam/:projectId',
      component: AllocationComponent
      },
      {
        path: 'userchatbox',
        component: UserChatboxComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
