package com.tcc.analisys;

import com.tcc.backend.models.Location;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

public class ExampleClustering {
    public static void main () {
        //All Locations
        System.out.println("All Locations");
        Location location = new Location();
        location.setAddress("a");
        location.setLat(1.0);
        location.setLng(2.0);

        Location location2 = new Location();
        location2.setAddress("a");
        location2.setLat(2.0);
        location2.setLng(3.0);

        //Each location should be add in a instance
        System.out.println("Each location should be add in a instance");

        Instance instance = new SparseInstance(2);
        instance.put(1, location.getLat());
        instance.put(2, location.getLng());

        Instance instance2 = new SparseInstance(2);
        instance2.put(1, location2.getLat());
        instance2.put(2, location2.getLng());

        //The dataset should receive all instances
        System.out.println("The dataset should receive all instances");
        Dataset dataset = new DefaultDataset();
        dataset.add(instance);
        dataset.add(instance2);

        //Running kmeans clusterer
        System.out.println("Running kmeans clusterer");
        Clusterer kMeansClusterer = new KMeans(2);
        Dataset[] clusters = kMeansClusterer.cluster(dataset);

        //log values console
        System.out.println(instance.getID() + ", " + instance2.getID());
    }
}
