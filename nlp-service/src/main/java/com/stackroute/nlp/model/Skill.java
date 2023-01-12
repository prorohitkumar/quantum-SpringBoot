package com.stackroute.nlp.model;

import com.stackroute.nlp.enums.SkillLevel;
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
