package com.tcc.backend.controllers.form;

import com.tcc.backend.domains.OriginType;
import com.tcc.backend.models.Location;
import com.tcc.backend.models.Occurrence;
import com.tcc.backend.models.OccurrenceType;
import com.tcc.backend.models.User;
import com.tcc.backend.resources.LocationRepository;
import com.tcc.backend.resources.OccurrenceTypeRepository;
import com.tcc.backend.resources.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Getter
@Setter
public class OccurrenceForm {
    private LocalDateTime dateTime;
    private Double lat;
    private Double lng;
    private String address;
    private String type;
    private String description;
    private OriginType origin;

    public Occurrence converter(LocationRepository locationRepository, OccurrenceTypeRepository occurrenceTypeRepository, UserRepository userRepository) {
        Location location = new Location(lat, lng, address);
        locationRepository.save(location);

        OccurrenceType occurrenceType = occurrenceTypeRepository.findByName(type);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);

        return new Occurrence(user, dateTime, location, occurrenceType, description, origin);
    }
}