package com.tcc.analisys.clusterer.models;

import com.tcc.backend.models.Location;
import com.tcc.backend.models.Occurrence;
import lombok.Getter;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Getter
public class OccurrenceClusteredDTO {
    private UUID id;
    private Occurrence centroid;
    private Location virtualCentroid;
    private List<Occurrence> otherOccurrences;
    private double radius;

    public OccurrenceClusteredDTO(Dataset occurrenceCluster) {
        this.id = UUID.randomUUID();

        this.centroid = convertToOccurrence(occurrenceCluster.instance(0));

        List<SparseInstance> occurrenceInstanceList = new ArrayList<>();
        for (int instanceIndex = 1; instanceIndex < occurrenceCluster.size(); instanceIndex++) {
            occurrenceInstanceList.add(((SparseInstance) occurrenceCluster.instance(instanceIndex)));
        }
        this.otherOccurrences = occurrenceInstanceList.stream().map(occurrenceInstance -> convertToOccurrence(occurrenceInstance)).collect(Collectors.toList());

        this.radius = getRadius(occurrenceInstanceList, (SparseInstance) occurrenceCluster.instance(0));

        List<Location> locations = new ArrayList<>();
        locations.add(this.centroid.getLocation());
        this.otherOccurrences.stream().forEach(occurrence -> locations.add(occurrence.getLocation()));
        this.virtualCentroid = GetCentrePointFromListOfLocations(locations);
    }

    private Location GetCentrePointFromListOfLocations(List<Location> coordList)
    {
        int total = coordList.size();

        double X = 0;
        double Y = 0;
        double Z = 0;

        for(Location location : coordList)
        {
            double lat = location.getLat() * Math.PI / 180;
            double lon = location.getLng() * Math.PI / 180;

            double x = Math.cos(lat) * Math.cos(lon);
            double y = Math.cos(lat) * Math.sin(lon);
            double z = Math.sin(lat);

            X += x;
            Y += y;
            Z += z;
        }

        X = X / total;
        Y = Y / total;
        Z = Z / total;

        double Lon = Math.atan2(Y, X);
        double Hyp = Math.sqrt(X * X + Y * Y);
        double Lat = Math.atan2(Z, Hyp);

        Location tempLocation = new Location();
        tempLocation.setLat(Lat * 180 / Math.PI);
        tempLocation.setLng(Lon * 180 / Math.PI);

        return tempLocation;
    }

    private Occurrence convertToOccurrence(Instance occurrenceInstanceList) {
        return ((OccurrenceInstance) occurrenceInstanceList.classValue()).getOccurrence();
    }

    private double getRadius(List<SparseInstance> occurrences, SparseInstance cluster) {
        if (occurrences.isEmpty()) {
            return 0.5; //Default radius
        }

        AtomicReference<Double> furtherDistance = new AtomicReference<Double>((double) Double.MIN_NORMAL);
        AtomicReference<SparseInstance> finalFurtherOccurrence = new AtomicReference<>();

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        occurrences.forEach((occurrence) -> {
            double currentDistance = Math.abs(euclideanDistance.calculateDistance(cluster, occurrence));
            if (currentDistance >= furtherDistance.get()) {
                furtherDistance.set(currentDistance);
                finalFurtherOccurrence.set(occurrence);
            }
        });

        double centroidOccurrenceLat = ((OccurrenceInstance) cluster.classValue()).getLat();
        double centroidOccurrenceLng = ((OccurrenceInstance) cluster.classValue()).getLng();
        double furtherOccurrenceLat = ((OccurrenceInstance) finalFurtherOccurrence.get().classValue()).getLat();
        double furtherOccurrenceLng = ((OccurrenceInstance) finalFurtherOccurrence.get().classValue()).getLng();

        if (Math.max(furtherOccurrenceLat, centroidOccurrenceLat) > Math.max(furtherOccurrenceLng, centroidOccurrenceLng)) {
            return Math.abs(furtherOccurrenceLat - centroidOccurrenceLat);
        }
        return Math.abs(furtherOccurrenceLng - centroidOccurrenceLng);
    }
}
