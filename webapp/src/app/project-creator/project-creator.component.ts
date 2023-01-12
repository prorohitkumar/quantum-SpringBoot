import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogPageComponent } from '../dialog-page/dialog-page.component';
import { Expertise, Manager, Project, ProjectStatus, Skill } from '../model/project';
import { SkillLevel } from '../model/user-profile';
import { ProjectService } from '../services/project.service';
import { TokenService } from '../services/token.service';
import { UserprofileService } from '../services/userprofile.service';
import Dropdown from '../Dropdown.json';
// import * as Dropdown from '../Dropdown.json';
import moment from 'moment';


@Component({
  selector: 'app-project-creator',
  templateUrl: './project-creator.component.html',
  styleUrls: ['./project-creator.component.css']
})
export class ProjectCreatorComponent implements OnInit 
{
  basicForm: FormGroup;
  expertiseForm: FormGroup;
  projectForm: FormGroup;

  expertiseArray: any;
  

  expertiseIndex = 0;
  showExpertiseFirst = true;

  skill: Skill;
  skillList: Array<Skill>;
  expertiseField: Expertise;
  project: Project;

  basic: SkillLevel = SkillLevel.BASIC;
  intermediate: SkillLevel = SkillLevel.INTERMEDIATE;
  advance: SkillLevel = SkillLevel.ADVANCE;

  visible = true;
  selectable = true;
  removable = true;

  jsonDataList: any = Dropdown;

  options: Array<String>;
  desoption: Array<String>;
  skilloptions: Array<String>;
  profOptions: Array<String>;
  ageGroup:Array<String>;
  experienceInYears:Array<String>;
  CtcRange:Array<String>;
  

  constructor(public router: Router, private dialog: MatDialog,private formBuilder: FormBuilder, private profileService: UserprofileService, private projectService: ProjectService, private tokenService: TokenService) { }

  ngOnInit(): void 
  {
    this.skill= new Skill();
    this.skillList = new Array<Skill>();
    this.expertiseField = new Expertise();
    this.expertiseField.skills = new Array<Skill>();
    this.project = new Project();
    this.project.manager = new Manager();
    this.project.expertise = new Array<Expertise>();


    this.options = this.jsonDataList.Data[0].City;
    this.desoption = this.jsonDataList.Data[0].Designation;
    this.skilloptions = this.jsonDataList.Data[0].TechnicalSkills;
    this.profOptions = this.jsonDataList.Data[0].ProfessionalDomain;
    this.ageGroup = this.jsonDataList.Data[0].AgeGroup;
    this.experienceInYears=this.jsonDataList.Data[0].ProfessionalExperienceInYears;
    this.CtcRange=this.jsonDataList.Data[0].CTC;
    this.options.sort();
    this.desoption.sort();
    this.skilloptions.sort();
    this.profOptions.sort();
    this.ageGroup.sort();
    this.CtcRange.sort();

 /*   this.basicForm= this.formBuilder.group(
      { 
         projectName: new FormControl('', Validators.required),
         projectDescription: new FormControl('', Validators.required),
         location: new FormControl('', Validators.required),
         durationInWeeks: new FormControl('', [Validators.required, Validators.min(1)]),
         startDate: new FormControl('', Validators.required),
      }); */
        
    this.projectForm = this.formBuilder.group
    ({
      projectName: ['', Validators.required],
      projectDescription: [''],
      location: ['',Validators.required],
      durationInWeeks: ['',[Validators.required, Validators.min(1)]],
      startDate: ['', Validators.required],
      expertise: this.formBuilder.array([this.createExpertise()])
    });
      
   /* this.expertiseForm = this.formBuilder.group(
    {
      noOfResources: new FormControl('', [ Validators.min(1), Validators.required]),
      designation: new FormControl('', [Validators.required]),
      minExperienceNeeded: new FormControl('', [ Validators.min(0), Validators.required]),
      maxExperienceNeeded: new FormControl('', [ Validators.min(0), Validators.required]),
      minAge: new FormControl('', [ Validators.min(20), Validators.required]),
      maxAge: new FormControl('', [ Validators.max(60), Validators.required]),
      minCTC: new FormControl('', [ Validators.min(0), Validators.required]),
      maxCTC: new FormControl('', [Validators.min(0), Validators.required]),
      domainExperience: new FormControl('', [Validators.required]),
      skillName: new FormControl(''),
      skillLevel: new FormControl('')
    }); */ 
  }

  createExpertise(): FormGroup 
  {
    return this.formBuilder.group
    ({
      noOfResources: new FormControl('', [ Validators.min(1), Validators.required]),
      designation: new FormControl('', [Validators.required]),
      experienceNeeded: new FormControl('', [ Validators.required]),
      age: new FormControl('', [ Validators.required]),
      ctc: new FormControl('', [ Validators.required]),
      domainExperience: new FormControl('', [Validators.required]),
      skillName: new FormControl(''),
      skillLevel: new FormControl('')
    });
  }


  addSkill()
  {   
      this.expertiseArray = this.projectForm.controls.expertise as FormArray;
      const expertiseLastIndex = this.expertiseArray.length - 1;


      this.skill.skillName = this.expertiseArray.value[expertiseLastIndex].skillName;
      this.skill.skillName = this.skill.skillName.trim();
      if(this.expertiseArray.value[expertiseLastIndex].skillLevel==="BASIC") this.skill.level = SkillLevel.BASIC;
      if(this.expertiseArray.value[expertiseLastIndex].skillLevel==="INTERMEDIATE") this.skill.level = SkillLevel.INTERMEDIATE;
      if(this.expertiseArray.value[expertiseLastIndex].skillLevel==="ADVANCE") this.skill.level = SkillLevel.ADVANCE;
      console.log(this.skill);
      this.expertiseField.skills.push(this.skill);
      this.skillList.push(this.skill);
      this.expertiseArray.value[expertiseLastIndex].skillName='';
      this.expertiseArray.value[expertiseLastIndex].skillLevel='';
      console.log(this.expertiseField.skills);
      this.skill = new Skill();

  }

  removeSkill(skill: Skill)
  {
    const index = this.skillList.indexOf(skill);
    const eindex = this.expertiseField.skills.indexOf(skill);

    if (index >= 0) {
      this.expertiseField.skills.splice(eindex, 1);
      this.skillList.splice(index, 1);
    }
  }


  addExpertise(indexvalue) 
  {
    this.showExpertiseFirst = false;
    this.expertiseIndex = indexvalue + 1;
    this.expertiseArray = this.projectForm.controls.expertise as FormArray;
    const expertiseLastIndex = this.expertiseArray.length - 1;
    console.log('project ', this.projectForm.value);
    console.log('expertise ', this.expertiseArray.value[expertiseLastIndex]);
    this.expertiseField.noOfResources = this.expertiseArray.value[expertiseLastIndex].noOfResources;
    this.expertiseField.designation = this.expertiseArray.value[expertiseLastIndex].designation;
    let ageRange= new String(this.expertiseArray.value[expertiseLastIndex].age);
    let ageSplit= ageRange.split("-");
    this.expertiseField.minAge = Number(ageSplit[0]);
    this.expertiseField.maxAge = Number(ageSplit[1]);
    let experienceRange= this.expertiseArray.value[expertiseLastIndex].experienceNeeded
    let experienceSplit = experienceRange.split("-");
    this.expertiseField.minExperienceNeeded =  Number(experienceSplit[0]);
    this.expertiseField.maxExperienceNeeded = Number(experienceSplit[1]);
    let ctcRange = new String(this.expertiseArray.value[expertiseLastIndex].ctc);
    let ctcSplit= ctcRange.split("-");
    this.expertiseField.minCTC =  Number(ctcSplit[0]);
    this.expertiseField.maxCTC = Number(ctcSplit[1]);
    this.expertiseField.domainExperience = this.expertiseArray.value[expertiseLastIndex].domainExperience;
    this.expertiseArray.push(this.createExpertise());

    this.project.expertise.push(this.expertiseField);
    this.expertiseField= new Expertise();
    this.expertiseField.skills = new Array<Skill>();

  }

  get expertiseControls() {
    return (this.projectForm.controls.expertise as FormArray).controls;
  }


  addProject()
  {

    this.profileService.getProfile().subscribe(response=>
    { 
      this.expertiseArray = this.projectForm.controls.expertise as FormArray;
      const expertiseLastIndex = this.expertiseArray.length - 1;
      console.log(this.expertiseArray.value);
      this.expertiseField.noOfResources = this.expertiseArray.value[expertiseLastIndex].noOfResources;
      this.expertiseField.designation = this.expertiseArray.value[expertiseLastIndex].designation;
      let ageRange= new String(this.expertiseArray.value[expertiseLastIndex].age);
      let ageSplit= ageRange.split("-");
      this.expertiseField.minAge = Number(ageSplit[0]);
      this.expertiseField.maxAge = Number(ageSplit[1]);
      let experienceRange= this.expertiseArray.value[expertiseLastIndex].experienceNeeded
      let experienceSplit = experienceRange.split("-");
      this.expertiseField.minExperienceNeeded =  Number(experienceSplit[0]);
      this.expertiseField.maxExperienceNeeded = Number(experienceSplit[1]);
      let ctcRange = new String(this.expertiseArray.value[expertiseLastIndex].ctc);
      let ctcSplit= ctcRange.split("-");
      this.expertiseField.minCTC =  Number(ctcSplit[0]);
      this.expertiseField.maxCTC = Number(ctcSplit[1]);
      this.expertiseField.domainExperience = this.expertiseArray.value[expertiseLastIndex].domainExperience;

      this.project.expertise.push(this.expertiseField);


      this.project.teamCreated = false;
      this.project.projectStatus = ProjectStatus.NOT_STARTED;
      this.project.manager.email = this.tokenService.getUser();
      this.project.manager.firstName = String(response.firstName);
      this.project.manager.lastName = String(response.lastName);

      this.project.projectName = this.projectForm.get('projectName').value;
      this.project.projectDescription = this.projectForm.get('projectDescription').value;
      this.project.location = this.projectForm.get('location').value;
      this.project.startDate = moment(this.projectForm.get('startDate').value).valueOf();
      this.project.durationInWeeks = this.projectForm.get('durationInWeeks').value;
      this.expertiseArray.push(this.createExpertise());

      this.projectForm.reset();
      console.log("Project Response before posting",this.project);

      this.projectService.postProjectDetails(this.project).subscribe(response=>
      { console.log(response); 
        this.project = new Project();
        this.project.manager = new Manager();
        this.project.expertise = new Array<Expertise>();
        this.expertiseField = new Expertise();
        this.expertiseField.skills = new Array<Skill>();
        this.skillList = new Array<Skill>();
        this.skill = new Skill();
        this.projectSubmitDialog('Project Successfully Added');

    
      },error=>{console.log(error);});
    });
  }

  projectSubmitDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: messageData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.router.navigate(['/dashboard']);
    });
  }
}
