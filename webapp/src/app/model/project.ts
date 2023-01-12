import { Domain, SkillLevel, Skills } from "./user-profile";

export class Project {
    projectId:string;
    projectName:string;
    projectDescription:string;
    location:string;
    startDate:number;
    teamCreated:boolean;
    durationInWeeks:any;
    manager:Manager;
    projectStatus:ProjectStatus;
    expertise:Array<Expertise>;
    allocatedResources:Array<AllocatedResources>;
    endDate: Date;
   
 
}

export class Manager{
    email:string;
    firstName:string;
    lastName:string;
}

export class Expertise{
    noOfResources:Number;
    designation:String;
    maxExperienceNeeded:Number;
    minExperienceNeeded:Number;
    minCTC:Number;
    maxCTC:Number;
    skills:Array<Skill>;
    minAge:Number;
    maxAge:Number;
    domainExperience:String;
}

export class  AllocatedResources{
    email:string;
    firstName:string;
    lastName:string;
    phoneNo:String;
    location:String;
    designation:String;
    CTC:Number;
    experienceInYrs:Number;
    domainExperience:Domain;
    skills:Array<Skills>;
    completedProjects:any;

}

export class Skill
{
    skillName: String;
    level: SkillLevel;
}


export enum ProjectStatus
{
 COMPLETED="COMPLETED",
 ONGOING="ONGOING",
 NOT_STARTED="NOT_STARTED" 
}
