import { BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule} from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatButtonModule} from '@angular/material/button';
import { MatIconModule  } from '@angular/material/icon';
import { MatGridListModule} from '@angular/material/grid-list';
import { MatSelectModule} from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatTooltipModule} from '@angular/material/tooltip';





import { DashboardComponent } from './dashboard/dashboard.component';
import { UserprofilePageComponent } from './userprofile-page/userprofile-page.component';
import { HomeComponent } from './home/home.component';


import { UserprofileService } from './services/userprofile.service';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DialogPageComponent } from './dialog-page/dialog-page.component';
import { ProjectDashboardComponent } from './project-dashboard/project-dashboard.component';
import { ProjectCardComponent } from './project-card/project-card.component';
import { OtpAuthenticationComponent } from './otp-authentication/otp-authentication.component';
import { ProjectCreationComponent } from './project-creation/project-creation.component';
import { ProjectCreatorComponent } from './project-creator/project-creator.component';
import { ResourceDashboardComponent } from './resource-dashboard/resource-dashboard.component';
import { ProjectViewComponent } from './project-view/project-view.component';
import { TeamDialogComponent } from './team-dialog/team-dialog.component';
import { AllocationComponent } from './allocation/allocation.component';
import { UserChatboxComponent } from './user-chatbox/user-chatbox.component';
import { ResourceCardComponent } from './resource-card/resource-card.component';



@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UserprofilePageComponent,
    HomeComponent,
    DialogPageComponent,
    ProjectDashboardComponent,
    ProjectCardComponent,
    OtpAuthenticationComponent,
    ProjectCreationComponent,
    ProjectCreatorComponent,
    ResourceDashboardComponent,
    ProjectViewComponent,
    TeamDialogComponent,
    AllocationComponent,
    UserChatboxComponent,
    ResourceCardComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatTabsModule,
    MatCardModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatSelectModule,
    MatChipsModule,
    HttpClientModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatMenuModule,
    MatExpansionModule,
    FlexLayoutModule, 
    MatProgressSpinnerModule,
    ScrollingModule,
    MatCheckboxModule,
    MatTooltipModule,
    
  ],

  providers: [UserprofileService],
  bootstrap: [AppComponent]
})
export class AppModule { }
