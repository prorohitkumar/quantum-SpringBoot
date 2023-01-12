export class UserProfile 
{
    email: String;
    firstName: String;
    lastName: String;
    userRole: String;
    phoneNo: String;
    dateOfBirth: Date;
    location: String;
    gender: Gender;
    anEmployee: boolean;
    availableForProject: boolean;
    showNotification: boolean;
    designation: String;
    ctc: Number;
    experienceInYrs: Number;
    domainExperiences: Array<Domain>;
    skills: Array<Skills>;
    completedProjects: Array<String>;
    image:String;
}

export class Skills
{
    skillName: String;
    level: SkillLevel;
}


export class Domain
{
    domainName: String;
    domainExperienceInYrs: Number;
}

export enum SkillLevel
{
    BASIC = "BASIC", 
    INTERMEDIATE = "INTERMEDIATE", 
    ADVANCE = "ADVANCE"
}

export enum Gender
{
    MALE = "MALE",
    FEMALE = "FEMALE",
    OTHER = "OTHER"
}

export class Message
{
    id: Boolean;
    text: String;
}



