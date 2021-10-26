package com.tcc.analisys.clusterer;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
import com.tcc.analisys.clusterer.models.OccurrenceInstance;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceClusterer {
    public List<OccurrenceClusteredDTO> run(List<OccurrenceInstance> occurrenceInstances) {
        Dataset dataset = new DefaultDataset();
        occurrenceInstances.stream().forEach(occurrenceInstance -> dataset.add(occurrenceInstance.toSparseInstance()));

        Clusterer kMeansClusterer = new KMeans(2);
        Dataset[] clusters = kMeansClusterer.cluster(dataset);

        return Arrays.stream(clusters).map(cluster -> new OccurrenceClusteredDTO(cluster)).collect(Collectors.toList());
    }
}