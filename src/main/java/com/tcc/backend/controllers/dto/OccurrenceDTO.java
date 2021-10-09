package com.tcc.backend.controllers.dto;

import com.tcc.backend.domains.OriginType;
import com.tcc.backend.models.Occurrence;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OccurrenceDTO {
    private final Long id;
    private final LocalDateTime dateTime;
    private final Double lat;
    private final Double lng;
    private final String address;
    private final String description;
    private final String type;
    private final OriginType origin;

    public OccurrenceDTO(Occurrence occurrence) {
        this.dateTime = occurrence.getDateTime();
        this.description = occurrence.getDescription();
        this.lat = occurrence.getLocation().getLat();
        this.lng = occurrence.getLocation().getLng();
        this.address = occurrence.getLocation().getAddress();
        this.id = occurrence.getId();
        this.type = occurrence.getType().getName();
        this.origin = occurrence.getOriginType();
    }

    public static List<OccurrenceDTO> converter(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::new).collect(Collectors.toList());
    }
}
