package com.stackroute.resourceallocate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity(label = "CTC")
@AllArgsConstructor
@NoArgsConstructor
public class CTC
{
    private Float name;
    private float ctc;
}
