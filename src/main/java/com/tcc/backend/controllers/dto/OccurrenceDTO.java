package com.tcc.backend.controllers.dto;

import com.tcc.backend.models.Occurrence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceDTO {
    private final Long id;
    private final LocalDateTime dateTime;
    private final Long lat;
    private final Long lng;
    private final String address;
    private final String description;

    public OccurrenceDTO(Occurrence occurrence) {
        this.dateTime = occurrence.getDateTime();
        this.description = occurrence.getDescription();
        this.lat = occurrence.getLocation().getLat();
        this.lng = occurrence.getLocation().getLng();
        this.address = occurrence.getLocation().getAddress();
        this.id = occurrence.getId();
    }

    public static List<OccurrenceDTO> converter(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getLat() {
        return lat;
    }

    public Long getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}