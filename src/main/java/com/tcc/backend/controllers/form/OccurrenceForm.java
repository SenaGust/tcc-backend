package com.tcc.backend.controllers.form;

import com.tcc.backend.models.Location;
import com.tcc.backend.models.Occurrence;
import com.tcc.backend.models.OccurrenceType;
import com.tcc.backend.models.User;
import com.tcc.backend.resources.LocationRepository;
import com.tcc.backend.resources.OccurrenceTypeRepository;
import com.tcc.backend.resources.UserRepository;

import java.time.LocalDateTime;

public class OccurrenceForm {
    private String authorName;
    private LocalDateTime dateTime;
    private Long lat;
    private Long lng;
    private String address;
    private String type;
    private String description;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Occurrence converter(LocationRepository locationRepository, OccurrenceTypeRepository occurrenceTypeRepository, UserRepository userRepository) {
        Location location = new Location(lat, lng, address);
        locationRepository.save(location);

        OccurrenceType occurrenceType = occurrenceTypeRepository.findByName(type);

        User user = userRepository.findByName(authorName);

        return new Occurrence(user, dateTime, location, occurrenceType, description);
    }
}