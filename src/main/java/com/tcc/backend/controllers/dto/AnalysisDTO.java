package com.tcc.backend.controllers.dto;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
import com.tcc.analisys.histogram.Histogram;
import com.tcc.backend.domains.Cluster;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AnalysisDTO {
    private List<Cluster> clusters;
    private Integer maxHistogramClass;

    public AnalysisDTO(List<OccurrenceClusteredDTO> occurrenceClusteredDTO) {
        this.clusters = new ArrayList<>();
        this.maxHistogramClass = occurrenceClusteredDTO.size();

        List<Integer> sizeOfClusters = occurrenceClusteredDTO.stream().map(c -> Math.max(c.getOtherOccurrences().size(), 1)).collect(Collectors.toList());
        Histogram histogram = new Histogram(sizeOfClusters);
        this.maxHistogramClass = histogram.howManyClasses();

        clusters.addAll(occurrenceClusteredDTO.stream().map(c -> new Cluster(c, histogram)).collect(Collectors.toList()));
    }
}
