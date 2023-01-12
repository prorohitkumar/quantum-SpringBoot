package com.stackroute.search.model;

import com.stackroute.search.enums.ProjectStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@Document(indexName = "projects", shards = 5)
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    @Id
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String location;
    private long startDate;
    private int durationInWeeks;
    private boolean teamCreated;
    private List<Expertise> expertise;
    private Manager manager;
    private ProjectStatus projectStatus;
    private List<UserProfile> allocatedResources;

}
