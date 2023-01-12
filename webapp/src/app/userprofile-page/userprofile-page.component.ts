import { ChangeDetectorRef, Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Skills, UserProfile, Domain, SkillLevel, Gender } from '../model/user-profile';
import { DialogData, UserprofileService } from '../services/userprofile.service';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogPageComponent } from '../dialog-page/dialog-page.component';
import { OtpAuthenticationComponent } from '../otp-authentication/otp-authentication.component';
import { OtpauthenticationService } from '../services/otpauthentication.service';
import { TokenService } from '../services/token.service';
import { MatFormFieldControl } from '@angular/material/form-field';
import Dropdown from '../Dropdown.json';
import { RouteConfigLoadEnd, Router } from '@angular/router';




@Component({
  selector: 'app-userprofile-page',
  templateUrl: './userprofile-page.component.html',
  styleUrls: ['./userprofile-page.component.css']
})
export class UserprofilePageComponent implements OnInit {

  userProfile: UserProfile = new UserProfile();
  skill: Skills = new Skills();
  skillLevelString: String;
  basic: SkillLevel = SkillLevel.BASIC;
  intermediate: SkillLevel = SkillLevel.INTERMEDIATE;
  advance: SkillLevel = SkillLevel.ADVANCE;
  domain: Domain = new Domain();
  date: Date = new Date();
  editForm: FormGroup;

  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  true: Boolean = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  selectedFile: File;
  submitted: boolean;
  imgbin: any;

  jsonDataList: any = Dropdown;

  options: Array<String>;
  desoption: Array<String>;
  skilloptions: Array<String>;
  profOptions: Array<String>;


  constructor(private userprofileService: UserprofileService, public router: Router, public dialog: MatDialog, private otpService: OtpauthenticationService, private tokenService: TokenService, public fb: FormBuilder, private cd: ChangeDetectorRef) { }
  registrationForm = this.fb.group({
    file: [null]
  });
  ngOnInit(): void {
    this.getProfile();

    this.editForm = new FormGroup(
      {
        firstName: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z ]*')]),
        lastName: new FormControl('', [Validators.required, Validators.pattern('[a-zA-Z ]*')]),
        phoneNo: new FormControl('', [Validators.required]),
        dateOfBirth: new FormControl('', Validators.required),
        location: new FormControl('', Validators.required),
        gender: new FormControl('', Validators.required),
        isAnEmployee: new FormControl('', Validators.required),
        isAvailableForProject: new FormControl('', Validators.required),
        designation: new FormControl('', [Validators.required]),
        CTC: new FormControl('', [Validators.required, Validators.min(0)]),
        experienceInYrs: new FormControl('', [Validators.required, Validators.min(1)]),
        skillName: new FormControl(''),
        skillLevel: new FormControl(''),
        domainNumber: new FormControl(''),
        domainName: new FormControl('', [Validators.min(1)]),
      }
    )
    console.log(this.jsonDataList);

    this.options = this.jsonDataList.Data[0].City;
    this.desoption = this.jsonDataList.Data[0].Designation;
    this.skilloptions = this.jsonDataList.Data[0].TechnicalSkills;
    this.profOptions = this.jsonDataList.Data[0].ProfessionalDomain;
    this.options.sort();
    this.desoption.sort();
    this.skilloptions.sort();
    this.profOptions.sort();
  }

  getProfile() {
    this.userprofileService.getProfile().subscribe(response => {
      this.userProfile = response;
      this.editForm.get("firstName").setValue(this.userProfile.firstName);
      this.editForm.get("lastName").setValue(this.userProfile.lastName);
      this.editForm.get("phoneNo").setValue(this.userProfile.phoneNo);
      this.editForm.get("dateOfBirth").setValue(this.userProfile.dateOfBirth);
      this.editForm.get("location").setValue(this.userProfile.location);
      if (this.userProfile.gender == Gender.MALE) this.editForm.get("gender").setValue("MALE");
      if (this.userProfile.gender == Gender.FEMALE) this.editForm.get("gender").setValue("FEMALE");
      if (this.userProfile.gender == Gender.OTHER) this.editForm.get("gender").setValue("OTHER");
      this.editForm.get("designation").setValue(this.userProfile.designation);
      this.editForm.get("CTC").setValue(this.userProfile.ctc);
      this.editForm.get("experienceInYrs").setValue(this.userProfile.experienceInYrs);
      if (this.userProfile.anEmployee == true) this.editForm.get("isAnEmployee").setValue("true");
      if (this.userProfile.anEmployee == false) this.editForm.get("isAnEmployee").setValue("false");
      if (this.userProfile.availableForProject == true) this.editForm.get("isAvailableForProject").setValue("true");
      if (this.userProfile.availableForProject == false) this.editForm.get("isAvailableForProject").setValue("false");
      if (this.userProfile.skills === null) this.userProfile.skills = new Array<Skills>();
      if (this.userProfile.domainExperiences === null) this.userProfile.domainExperiences = new Array<Domain>();
      if (this.userProfile.image == null) this.imageUrl = 'https://api-private.atlassian.com/users/8b3597e8a7d1d8f2ed7f58bcab1946b8/avatar';
      else this.imageUrl = this.userProfile.image;
    },
      error => {
        console.log(error);
      });
  }



  editProfile() {
    this.otpService.sendOTP().subscribe(response => {
      console.log("Sent OTP", response);
      // this.openOTP();
      let validationResult = this.openOTP();
      console.log(validationResult);


    },
      error => {
        console.log("Error while sending OTP", error);
      }
    );
  }

  openDialog(messageData: string): void {
    const dialogRef = this.dialog.open(DialogPageComponent, {
      width: '400px',
      data: { messageData: messageData }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openOTP() {
    const otpRef = this.dialog.open(OtpAuthenticationComponent,
      {
        width: '400px'
      });
    otpRef.afterClosed().subscribe(response => {
      console.log(response);
      // if(response==='Entered Otp is valid')
      //   { 
      if (this.editForm.get("isAnEmployee").value == "true") this.editForm.get("isAnEmployee").setValue(true);
      if (this.editForm.get("isAnEmployee").value == "false") this.editForm.get("isAnEmployee").setValue(false);
      if (this.editForm.get("isAvailableForProject").value == "true") this.editForm.get("isAvailableForProject").setValue(true);
      if (this.editForm.get("isAvailableForProject").value == "false") this.editForm.get("isAvailableForProject").setValue(false);

      this.userProfile.firstName = this.editForm.get("firstName").value;
      this.userProfile.lastName = this.editForm.get("lastName").value;
      this.userProfile.phoneNo = this.editForm.get("phoneNo").value;
      this.userProfile.dateOfBirth = this.editForm.get("dateOfBirth").value;
      this.userProfile.location = this.editForm.get("location").value;
      if (this.editForm.get("gender").value == "MALE") this.userProfile.gender = Gender.MALE;
      if (this.editForm.get("gender").value == "FEMALE") this.userProfile.gender = Gender.FEMALE;
      if (this.editForm.get("gender").value == "OTHER") this.userProfile.gender = Gender.OTHER;
      this.userProfile.anEmployee = this.editForm.get("isAnEmployee").value;
      this.userProfile.availableForProject = this.editForm.get("isAvailableForProject").value;
      this.userProfile.showNotification = false;
      this.userProfile.designation = this.editForm.get("designation").value;
      this.userProfile.ctc = this.editForm.get("CTC").value;
      this.userProfile.experienceInYrs = this.editForm.get("experienceInYrs").value;

      this.userprofileService.setProfile(this.userProfile).subscribe(response => {
        console.log("Sent Object", response);
        // this.onUpload();
      }
      );

      this.openDialog("The User Profile has been successfully edited.");
      // }
      // else
      // {
      //   this.openDialog("The OTP Entered Was Wrong. Validation Failed.");
      // }
    }, error => {
      console.log(error);
    });
  }


  addSkill(): void {


    this.skill.skillName = this.editForm.get('skillName').value;
    this.skill.skillName = this.skill.skillName.trim();
    if (this.editForm.get('skillLevel').value === "BASIC") this.skill.level = SkillLevel.BASIC;
    if (this.editForm.get('skillLevel').value === "INTERMEDIATE") this.skill.level = SkillLevel.INTERMEDIATE;
    if (this.editForm.get('skillLevel').value === "ADVANCE") this.skill.level = SkillLevel.ADVANCE;
    console.log(this.skill);
    this.userProfile.skills.push(this.skill);
    this.editForm.get('skillName').setValue('');
    this.editForm.get('skillLevel').setValue('');
    this.skill = new Skills();

  }


  removeSkill(skill: Skills): void {
    const index = this.userProfile.skills.indexOf(skill);

    if (index >= 0) {
      this.userProfile.skills.splice(index, 1);
    }
  }

  stringSkill(skillLevel: SkillLevel) {
    if (skillLevel === SkillLevel.BASIC) return "BASIC";
    if (skillLevel === SkillLevel.INTERMEDIATE) return "INTERMEDIATE";
    if (skillLevel === SkillLevel.ADVANCE) return "ADVANCE";
  }

  addDomain(): void 
  {
    this.domain.domainName = this.editForm.get('domainName').value;
    this.domain.domainExperienceInYrs = this.editForm.get('domainNumber').value;
    this.userProfile.domainExperiences.push(this.domain);
    this.domain = new Domain();

  }

   rerouteAI()
   {
    this.router.navigate(['/dashboard/userchatbox']);

   } 


  removeDomain(domain: Domain): void {
    const index = this.userProfile.domainExperiences.indexOf(domain);

    if (index >= 0) {
      this.userProfile.domainExperiences.splice(index, 1);
    }
  }


  onUpload() {
    // console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('image', this.selectedFile);
    uploadImageData.append('email', this.userProfile.email.toString());

    //Make a call to the Spring Boot Application to save the image
    this.userprofileService.onUpload(uploadImageData).subscribe((response) => {
      console.log(response);
    }
    );
    //  }


  }

  @ViewChild('fileInput') el: ElementRef;
  imageUrl: any
  editFile: boolean = true;
  removeUpload: boolean = false;

  uploadFile(event) {
    let reader = new FileReader(); // HTML5 FileReader API
    let file = event.target.files[0];
    //this.selectedFile=file;
    if (event.target.files && event.target.files[0]) {
      reader.readAsDataURL(file);

      // When file uploads set it to file formcontrol
      reader.onload = () => {
        this.imageUrl = reader.result;
        this.selectedFile = this.imageUrl;
        //if(this.selectedFile==this.imgbin)
        console.log(typeof this.selectedFile);
        console.log(this.selectedFile);
        this.onUpload();
        this.registrationForm.patchValue({
          file: reader.result
        });
        this.editFile = false;
        this.removeUpload = true;
      }
      // ChangeDetectorRef since file is loading outside the zone
      this.cd.markForCheck();
    }
     //this.onUpload();
  }

  dataChecker()
  {
    if(this.userProfile.firstName!=null)
    {
      return true;
    }
    else return false;
  }
 
}

