package com.tcc.backend.domains;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
import com.tcc.analisys.histogram.Histogram;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Cluster {
    private UUID id;
    private Double lat;
    private Double lng;
    private Double radius;
    private List otherNodes;
    private Integer histogramClass;

    public Cluster(OccurrenceClusteredDTO occurrenceClusteredDTO, Histogram histogram) {
        this.id = occurrenceClusteredDTO.getId();
        this.lat = occurrenceClusteredDTO.getVirtualCentroid().getLat();
        this.lng = occurrenceClusteredDTO.getVirtualCentroid().getLng();
        this.radius = occurrenceClusteredDTO.getRadius();
        this.otherNodes = occurrenceClusteredDTO.getOtherOccurrences();
        this.histogramClass = histogram.whatsTheClass(occurrenceClusteredDTO.getOtherOccurrences().size());
    }
}
