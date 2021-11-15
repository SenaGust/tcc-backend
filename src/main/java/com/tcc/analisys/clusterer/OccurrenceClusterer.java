package com.tcc.analisys.clusterer;

import com.tcc.analisys.clusterer.models.OccurrenceClusteredDTO;
import com.tcc.analisys.clusterer.models.OccurrenceInstance;
import net.sf.javaml.clustering.IterativeKMeans;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceClusterer {
    public List<OccurrenceClusteredDTO> run(List<OccurrenceInstance> occurrenceInstances) {
        Dataset dataset = new DefaultDataset();
        occurrenceInstances.stream().forEach(occurrenceInstance -> dataset.add(occurrenceInstance.toSparseInstance()));

        IterativeKMeans iterativeKMeans = new IterativeKMeans(3, generateKMax(occurrenceInstances.size()), 100, new EuclideanDistance(), new SumOfSquaredErrors());
        Dataset[] clusters = iterativeKMeans.cluster(dataset);

        return Arrays.stream(clusters).map(cluster -> new OccurrenceClusteredDTO(cluster)).collect(Collectors.toList());
    }


    private int generateKMax(int totalItems) {
        return Math.max(totalItems / 3, 3);
    }
}
