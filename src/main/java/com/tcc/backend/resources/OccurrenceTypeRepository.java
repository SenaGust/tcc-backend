package com.tcc.backend.resources;

import com.tcc.backend.models.OccurrenceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccurrenceTypeRepository extends JpaRepository<OccurrenceType, Long> {
    OccurrenceType findByName(String name);
}
