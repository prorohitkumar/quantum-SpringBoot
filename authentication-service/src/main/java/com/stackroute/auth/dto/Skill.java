package com.stackroute.auth.dto;

import com.stackroute.auth.enums.SkillLevel;
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
