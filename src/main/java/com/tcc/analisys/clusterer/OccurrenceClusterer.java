package com.tcc.analisys.clusterer;

import com.tcc.analisys.clusterer.models.OccurrenceInstance;
import com.tcc.backend.models.Occurrence;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.distance.EuclideanDistance;

public class OccurrenceClusterer {
    public static void main(String[] args) {
        //All Locations
        System.out.println("All Locations");
        OccurrenceInstance occurrenceInstance = new OccurrenceInstance();
        occurrenceInstance.setLat(1.0);
        occurrenceInstance.setLng(2.0);
        occurrenceInstance.setOccurrence(new Occurrence());

        OccurrenceInstance occurrenceInstance2 = new OccurrenceInstance();
        occurrenceInstance2.setLat(20.0);
        occurrenceInstance2.setLng(21.0);
        occurrenceInstance2.setOccurrence(new Occurrence());

        OccurrenceInstance occurrenceInstance3 = new OccurrenceInstance();
        occurrenceInstance3.setLat(18.0);
        occurrenceInstance3.setLng(19.0);
        occurrenceInstance3.setOccurrence(new Occurrence());

        OccurrenceInstance occurrenceInstance4 = new OccurrenceInstance();
        occurrenceInstance4.setLat(22.0);
        occurrenceInstance4.setLng(23.0);
        occurrenceInstance4.setOccurrence(new Occurrence());


        //The dataset should receive all instances
        System.out.println("The dataset should receive all instances");
        Dataset dataset = new DefaultDataset();
        dataset.add(occurrenceInstance.toSparseInstance());
        dataset.add(occurrenceInstance2.toSparseInstance());
        dataset.add(occurrenceInstance3.toSparseInstance());
        dataset.add(occurrenceInstance4.toSparseInstance());

        //Running kmeans clusterer
        System.out.println("Running kmeans clusterer");
        Clusterer kMeansClusterer = new KMeans(2);
        Dataset[] clusters = kMeansClusterer.cluster(dataset);

        SparseInstance[] sparseInstanceList = new SparseInstance[clusters[0].size()];

        for (int i = 0; i < clusters[0].size(); i++) {
            sparseInstanceList[i] = ((SparseInstance) clusters[0].instance(i));
        }


        double teste = getRadius(sparseInstanceList);
        System.out.println("radius: " + teste);

        //log values console
        //System.out.println("center of a cluster 0: " + ((OccurrenceInstance) clusters[0].instance(0).classValue()).getLng() + " length: " + clusters[0].size());
        //System.out.println("center of a cluster 1: " + ((OccurrenceInstance) clusters[1].instance(0).classValue()).getLng() + " length: " + clusters[1].size());
    }


    public static double getRadius(SparseInstance[] occurrences) {
        EuclideanDistance euclideanDistance = new EuclideanDistance();

        if (occurrences.length < 2) {
            double DEFAULT_RADIUS = 4;
            return DEFAULT_RADIUS;
        }

        double furtherDistance = euclideanDistance.calculateDistance(occurrences[0], occurrences[1]);
        SparseInstance furtherOccurrence = occurrences[1];

        for (int i = 2; i < occurrences.length; i++) {
            double currentDistance = euclideanDistance.calculateDistance(occurrences[0], occurrences[i]);
            if (currentDistance > furtherDistance) {
                furtherDistance = currentDistance;
                furtherOccurrence = occurrences[i];
            }
        }

        double furtherOccurrenceLat = ((OccurrenceInstance) furtherOccurrence.classValue()).getLat();
        double furtherOccurrenceLng = ((OccurrenceInstance) furtherOccurrence.classValue()).getLng();
        double centroidOccurrenceLat = ((OccurrenceInstance) occurrences[0].classValue()).getLat();
        double centroidOccurrenceLng = ((OccurrenceInstance) occurrences[0].classValue()).getLng();

        double result = furtherOccurrenceLng - centroidOccurrenceLng;

        if (Math.max(furtherOccurrenceLat, centroidOccurrenceLat) > Math.max(furtherOccurrenceLng, centroidOccurrenceLng)) {
            result =  furtherOccurrenceLat - centroidOccurrenceLat;
        }

        return result < 0 ? result * -1 : result;
    }
}
