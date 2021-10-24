package com.tcc.analisys.clusterer.models;

import com.tcc.backend.models.Occurrence;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class OccurrenceClusteredDTO {
    private int id;
    private Occurrence centroid;
    private Occurrence[] otherOccurrences;
    private double radius;

    public OccurrenceClusteredDTO(int id, Occurrence[] occurrences, double radius) {
        this.id = id;
        this.centroid = occurrences[0];
        this.otherOccurrences = Arrays.copyOfRange(occurrences, 1, occurrences.length);;
        this.radius = radius;
    }
}
