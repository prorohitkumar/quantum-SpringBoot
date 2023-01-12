import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AllocatedResources, Manager, Project } from '../model/project';
import { ProjectService } from '../services/project.service';
import { SearchService } from '../services/search.service';
import { TokenService } from '../services/token.service';
import { NlpService } from '../services/nlp.service';

declare var webkitSpeechRecognition: any;

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.css'],
})
export class ProjectDashboardComponent implements OnInit {

  recognition =  new webkitSpeechRecognition();
  isStoppedSpeechRecog = false;
  public text = '';
  tempWords:any;

  content:Array<any>=new Array<any>();
  searchHits:Array<any>;
  filterName:string;
  searchData: Project = new Project();
  endDate: any;
  currentProject: Array<Project> = new Array<Project>();
  manager: Array<Manager> = new Array<Manager>();
  currentManager: Manager;
   store:any;
  searchForm:FormGroup;

  results: any[] = [];
  queryField: FormControl = new FormControl();
  constructor(private projectService: ProjectService, private searchService: SearchService,
    private nlpservice:NlpService, private tokenService: TokenService) {}

  projects: Array<Project> = new Array<Project>();
  project: Project = new Project();
  allocatedResource: AllocatedResources = new AllocatedResources();
  searchField: String;
  searchValue: String ='';
  ngOnInit(): void {

    this.searchForm=new FormGroup({
      searchbar:new FormControl('')
    });

    if(this.tokenService.getRole()=="MANAGER"){

      this.projectService.getProjectDetails().subscribe(response => {
        console.log(response);
        for(this.project of response)
        {
          this.projects.push(this.project);
        }
        this.projects.reverse();
        this.store=response;
  
      },
        error => {
          console.log(error);
        });
    }
    else{
      this.projectService.getResourceProjectDetails().subscribe(response => {
        console.log(response);
        for(this.project of response)
        {
          this.projects.push(this.project);
        }
        this.store=response;
  
      },
        error => {
          console.log(error);
        });
    }

      this.recognition.interimResults = true;
      this.recognition.lang = 'en-GB';

      this.recognition.addEventListener('result', (e) => {
      const transcript = Array.from(e.results)
        .map((result) => result[0])
        .map((result) => result.transcript)
        .join('');
      this.tempWords = transcript;
      this.searchForm.get('searchbar').setValue(this.tempWords);
      console.log(transcript);
    });
    
    }

  searchFun() {
    console.log("search value"+this.searchForm.value.searchbar);
    const temp = { "Field": this.searchField, "Value": this.searchValue };
    this.nlpservice.searchProjects(this.searchForm.value.searchbar).subscribe(response => {
      console.log(response);
      
      this.projects=response;
      this.content=[];
      this.searchValue='';
      
      // this.currentProject.push(response);
      //this.projects = response;

    },
      error => {
        console.log(error);
      });


  }

  saveInput(event) {
   // console.log(event.value);
    this.searchValue = event.value;
  }

 clear(){
   this.projects=this.store;
   this.filterName = '';
   this.searchValue=''; 
 }

start() {
this.isStoppedSpeechRecog = false;
this.recognition.start();
console.log("Speech recognition started");
this.isStoppedSpeechRecog=true;
this.recognition.addEventListener('end', (condition) => {
  if (this.isStoppedSpeechRecog) {
    this.recognition.stop();
    this.searchFun();
    console.log("End speech recognition")
  } else {
      this.wordConcat()
      this.recognition.start();
    }
  });
  
}

stop() 
{
  this.isStoppedSpeechRecog = true;
  this.wordConcat();
  this.recognition.stop();
  console.log("End speech recognition")
}

wordConcat() 
{
  this.text = this.text + ' ' + this.tempWords + '.';
}

} 

