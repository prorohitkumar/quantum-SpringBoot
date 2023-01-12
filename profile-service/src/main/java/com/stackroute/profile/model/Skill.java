package com.stackroute.profile.model;

import com.stackroute.profile.enums.SkillLevel;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Skill
{
    private String skillName;
    private SkillLevel level;
}
