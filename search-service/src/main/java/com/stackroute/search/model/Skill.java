package com.stackroute.search.model;

import com.stackroute.search.enums.SkillLevel;
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
