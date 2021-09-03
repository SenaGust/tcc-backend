package com.tcc.backend.controllers;

import com.tcc.backend.controllers.dto.OccurrenceDTO;
import com.tcc.backend.models.Location;
import com.tcc.backend.models.Occurrence;
import com.tcc.backend.models.OccurrenceType;
import com.tcc.backend.models.User;
import com.tcc.backend.resources.OccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceController {
    @Autowired
    private OccurrenceRepository occurrenceRepository;

    @GetMapping
    public List<OccurrenceDTO> list() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        return OccurrenceDTO.converter(occurrences);
    }
}
