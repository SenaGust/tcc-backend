package com.tcc.backend.controllers.dto;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
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

    public AnalysisDTO (List<OccurrenceClusteredDTO> occurrenceClusteredDTO) {
        this.clusters = new ArrayList<>();

        clusters.addAll(occurrenceClusteredDTO.stream().map(c -> new Cluster(c)).collect(Collectors.toList()));
    }
}
