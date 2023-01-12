import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Domain, Gender, Message, SkillLevel, Skills, UserProfile } from '../model/user-profile';
import { DialogFlowService } from '../services/dialog-flow.service';
import Dropdown from '../Dropdown.json';
import { Skill } from '../model/project';
import { UserprofileService } from '../services/userprofile.service';
import { DialogPageComponent } from '../dialog-page/dialog-page.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';


@Component({
  selector: 'app-user-chatbox',
  templateUrl: './user-chatbox.component.html',
  styleUrls: ['./user-chatbox.component.css']
})
export class UserChatboxComponent implements OnInit {

  chatboxForm: FormGroup;
  phoneForm: FormGroup;
  desigForm: FormGroup;
  domainForm: FormGroup;
  skillForm: FormGroup;

  basic: SkillLevel = SkillLevel.BASIC;
  intermediate: SkillLevel = SkillLevel.INTERMEDIATE;
  advance: SkillLevel = SkillLevel.ADVANCE;

  phoneChecker: Boolean = false;
  genderChecker: Boolean = false; 
  statusChecker: Boolean = false;
  desigChecker: Boolean = false;
  domainChecker: Boolean = false;
  skillChecker: Boolean = false;
  submitChecker: Boolean = false;

  jsonDataList: any = Dropdown;

  options: Array<String>;
  

  true: Boolean = true;
  false: Boolean= false;
  empty: number = 0;

  message: Message = new Message();
  dataArray: Array<String> = new Array<String>();
  domains: Array<Domain> = new Array<Domain>();
  skills: Array<Skill> = new Array<Skill>();
  messages: Array<Message> = new Array<Message>();

  constructor(private formBuilder: FormBuilder, private dialogFlowService: DialogFlowService, public router: Router, private userProfileService :UserprofileService, private dialog: MatDialog) { }


  ngOnInit(): void 
  {

    this.chatboxForm = this.formBuilder.group({ chatInput: ['', Validators.required]});
    this.phoneForm = new FormGroup({ phoneInput: new FormControl('', [Validators.required])})
    this.desigForm = new FormGroup({ desigInput: new FormControl('')});
    this.domainForm = new FormGroup({ domainName: new FormControl(''), domainNumber: new FormControl('')});
    this.skillForm = new FormGroup({ skillName: new FormControl(''), skillLevel: new FormControl('')});

    this.getDialogFlowMessages('create profile');

  }

  getDialogFlowMessages(command: String)
  {
    this.dialogFlowService.getDialogFlow(command).subscribe(response=>
      {

          let tempMessage = new Message();
          tempMessage.text=response.split('.')[0] + '.';
          this.dataArray.push(response.split('.')[1]);
          console.log(this.dataArray);
          tempMessage.id = true;
          this.messages.push(tempMessage);
       
        if(command==='create profile')
        { 
          
          tempMessage = new Message();
          tempMessage.text='I will guide you through profile creation. Now first.';
          tempMessage.id = true;
          this.messages.push(tempMessage);
        }
        this.dialogFlowService.getDialogFlow('next').subscribe(response=>
          {
            let tempMessage = new Message();
            tempMessage.text=response;
            tempMessage.id = true;
            this.messages.push(tempMessage);
            if(response.indexOf('phone')>0 || response.indexOf('CTC')>0 || response.indexOf('your experience')>0)
            {
              this.phoneChecker = true;
            }
            if(response.indexOf('gender')>0)
            {
              this.genderChecker = true;
            }
            if(response.indexOf('Employee')>0 || response.indexOf('available')>0)
            {
              this.statusChecker = true;
            }
            if(response.indexOf('designation')>0 || response.indexOf('location')>0)
            {
              this.desigChecker = true;
              if( response.indexOf('designation')>0 ) this.options = this.jsonDataList.Data[0].Designation;
              if( response.indexOf('location')>0 ) this.options = this.jsonDataList.Data[0].City; 
            }
            if(response.indexOf('domain')>0)
            {
              this.domainChecker = true;
              this.options = this.jsonDataList.Data[0].ProfessionalDomain;
            }
            if(response.indexOf('skill')>0)
            {
              this.skillChecker = true;
              this.options = this.jsonDataList.Data[0].TechnicalSkills;
            }
            if(response.indexOf('data')>0)
            {
              this.submitChecker = true;
            }
          });
      });

  }

  sendMessage()
  {
    let tempMessage = new Message();
    tempMessage.id = false;
    tempMessage.text = this.chatboxForm.get('chatInput').value;
    if(this.phoneChecker==true)
    {
      tempMessage.text = this.phoneForm.get('phoneInput').value;
    }
    if(this.desigChecker==true)
    {
      tempMessage.text = this.desigForm.get('desigInput').value;
    }
    if(this.domainChecker==true)
    {
      for(let domain of this.domains)
      {
        tempMessage = new Message();
        tempMessage.id = false;
        tempMessage.text = domain.domainName;
        this.messages.push(tempMessage);
      }
      tempMessage = new Message();
      tempMessage.id = false;
      tempMessage.text="Above is the list of all the domains selected.";
    }
    if(this.skillChecker==true)
    {
      for(let skill of this.skills)
      {
        tempMessage = new Message();
        tempMessage.id = false;
        tempMessage.text = skill.skillName;
        this.messages.push(tempMessage);
      }
      tempMessage = new Message();
      tempMessage.id = false;
      tempMessage.text="Above is the list of all the skills selected.";
    }
    console.log(tempMessage.text);
    this.messages.push(tempMessage);
    this.getDialogFlowMessages(tempMessage.text);
    this.phoneChecker=false;
    this.desigChecker=false;
    this.domainChecker=false;
    this.skillChecker = false;
    this.chatboxForm.reset();
    this.phoneForm.reset();
    this.desigForm.reset();
    this.domainForm.reset();
    this.skillForm.reset();

  }

  selectGender(gender: String)
  {
      let tempMessage = new Message();
      tempMessage.id = false;
      tempMessage.text = gender;
      this.messages.push(tempMessage);
      this.getDialogFlowMessages(gender);
      this.genderChecker = false;
  }

  selectStatus(status: String)
  {
    let tempMessage = new Message();
    tempMessage.id = false;
    tempMessage.text = status;
    this.messages.push(tempMessage);
    this.getDialogFlowMessages(status);
    this.statusChecker = false;

  }

  addDomain()
  {   let domain = new Domain();
      domain.domainName = this.domainForm.get('domainName').value;
      domain.domainExperienceInYrs = this.domainForm.get('domainNumber').value;
      console.log(domain);
      this.domains.push(domain);
      this.domainForm.reset();
  }

  removeDomain(domain: Domain): void {
    const index = this.domains.indexOf(domain);

    if (index >= 0) {
      this.domains.splice(index, 1);
    }
  }

  addSkill()
  {   let skill = new Skill();
      skill.skillName = this.skillForm.get('skillName').value;
      if (this.skillForm.get('skillLevel').value === "BASIC") skill.level = SkillLevel.BASIC;
      if (this.skillForm.get('skillLevel').value === "INTERMEDIATE") skill.level = SkillLevel.INTERMEDIATE;
      if (this.skillForm.get('skillLevel').value === "ADVANCE")  skill.level = SkillLevel.ADVANCE;
      console.log(skill);
      this.skills.push(skill);
      this.skillForm.reset();
  }

  removeSkill(skill: Skill): void {
    const index = this.skills.indexOf(skill);

    if (index >= 0) {
      this.domains.splice(index, 1);
    }
  }

  submit()
  {
      let userProfile = new UserProfile();
      this.userProfileService.getProfile().subscribe(response=>
        { userProfile = response;
          userProfile.firstName = this.dataArray[1];
          userProfile.lastName = this.dataArray[2]; 
          userProfile.phoneNo = this.dataArray[3]; 
          userProfile.dateOfBirth = new Date(String(this.dataArray[4]));
          userProfile.location = this.dataArray[5].trim(); 
          if(this.dataArray[6].trim()=="MALE") userProfile.gender = Gender.MALE;
          if(this.dataArray[6].trim()=="FEMALE") userProfile.gender = Gender.FEMALE;
          if(this.dataArray[6].trim()=="OTHER") userProfile.gender = Gender.OTHER;
          if(this.dataArray[7].trim()=="TRUE") userProfile.anEmployee = true;
          if(this.dataArray[7].trim()=="FALSE") userProfile.anEmployee = false;
          if(this.dataArray[8].trim()=="TRUE") userProfile.availableForProject = true;
          if(this.dataArray[8].trim()=="FALSE") userProfile.availableForProject = false;
          userProfile.designation = this.dataArray[9].trim();
          userProfile.ctc = Number(this.dataArray[10]);
          userProfile.experienceInYrs = new Number(this.dataArray[11]);
          userProfile.domainExperiences = this.domains;
          userProfile.skills = this.skills;
          this.userProfileService.setProfile(userProfile).subscribe
          (response=>
            {
              console.log(response);
              this.openSubmitDialog("User Profile has been Updated.");
            }
          )
        });

  }

  retry()
  {
    this.dataArray= new Array<String>();
    this.skills = new Array<Skill>();
    this.domains = new Array<Domain>();
    this.phoneChecker=false;
    this.desigChecker=false;
    this.domainChecker=false;
    this.skillChecker = false;
    this.submitChecker= false;
    this.getDialogFlowMessages('create profile');
  }


  openSubmitDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: {messageData: messageData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.router.navigate(['/dashboard/user']);
    });
  }




}
