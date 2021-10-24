package com.tcc.analisys.clusterer.models;

import com.tcc.backend.models.Occurrence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.javaml.core.SparseInstance;

@NoArgsConstructor
@Getter
@Setter
public class OccurrenceInstance implements Comparable<OccurrenceInstance> {
    private int id;
    private Occurrence occurrence;
    private Double lat;
    private Double lng;

    public OccurrenceInstance(Occurrence occurrence) {
        this.occurrence = occurrence;
        this.lat = occurrence.getLocation().getLat();
        this.lng = occurrence.getLocation().getLng();
    }

    public SparseInstance toSparseInstance() {
        SparseInstance instance = new SparseInstance(2, this);
        instance.put(0,  this.lat);
        instance.put(1, this.lng);

        this.id = id = instance.getID();

        return instance;
    }

    @Override
    public int compareTo(OccurrenceInstance o) {
        return o.id;
    }
}
