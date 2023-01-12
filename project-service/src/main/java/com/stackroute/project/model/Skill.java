package com.stackroute.project.model;

import com.stackroute.project.enums.SkillLevel;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Skill {

    private String skillName;
    private SkillLevel level;

}
