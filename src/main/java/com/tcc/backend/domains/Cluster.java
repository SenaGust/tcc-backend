package com.tcc.backend.domains;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Cluster {
    private UUID id;
    private Double lat;
    private Double lng;
    private Double radius;
    private String color;

    public Cluster(OccurrenceClusteredDTO occurrenceClusteredDTO) {
        this.id = occurrenceClusteredDTO.getId();
        this.lat = occurrenceClusteredDTO.getVirtualCentroid().getLat();
        this.lng = occurrenceClusteredDTO.getVirtualCentroid().getLng();
        this.color = "blue";
        this.radius = occurrenceClusteredDTO.getRadius();
    }
}
