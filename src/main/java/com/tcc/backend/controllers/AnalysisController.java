package com.tcc.backend.controllers;

import com.tcc.analisys.clusterer.OccurrenceClusterer;
import com.tcc.analisys.clusterer.models.OccurrenceInstance;
import com.tcc.backend.controllers.dto.AnalysisDTO;
import com.tcc.backend.models.Occurrence;
import com.tcc.backend.resources.OccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
public class AnalysisController {
    @Autowired
    private OccurrenceRepository occurrenceRepository;

    @GetMapping
    public AnalysisDTO list() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        OccurrenceClusterer occurrenceClusterer = new OccurrenceClusterer();
        List<OccurrenceInstance> occurrenceInstances = occurrences.stream().map(occurrence -> new OccurrenceInstance(occurrence)).collect(Collectors.toList());
        return new AnalysisDTO(occurrenceClusterer.run(occurrenceInstances));
    }

}
