package com.tcc.backend.controllers;

import com.tcc.backend.controllers.dto.OccurrenceDTO;
import com.tcc.backend.controllers.form.OccurrenceForm;
import com.tcc.backend.models.Occurrence;
import com.tcc.backend.resources.LocationRepository;
import com.tcc.backend.resources.OccurrenceRepository;
import com.tcc.backend.resources.OccurrenceTypeRepository;
import com.tcc.backend.resources.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceController {
    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceTypeRepository occurrenceTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<OccurrenceDTO> list() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        return OccurrenceDTO.converter(occurrences);
    }

    @PostMapping
    public ResponseEntity<OccurrenceDTO> create(@RequestBody OccurrenceForm occurrenceForm, UriComponentsBuilder uriBuilder) {
        Occurrence occurrence = occurrenceForm.converter(locationRepository, occurrenceTypeRepository, userRepository);
        occurrenceRepository.save(occurrence);

        URI uri = uriBuilder.path("/occurrences/{id}").buildAndExpand(occurrence.getId()).toUri();

        return ResponseEntity.created(uri).body(new OccurrenceDTO(occurrence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Occurrence> optional = occurrenceRepository.findById(id);
        if (optional.isPresent()) {
            occurrenceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
