package com.stackroute.allocationengine.model;

import com.stackroute.allocationengine.enums.SkillLevel;
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
