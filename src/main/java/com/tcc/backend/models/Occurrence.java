package com.tcc.backend.models;

import com.tcc.backend.domains.OriginType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Occurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    private LocalDateTime dateTime;
    @OneToOne
    private Location location;
    @ManyToOne
    private OccurrenceType type;
    private String description;
    @Enumerated(EnumType.STRING)
    private OriginType originType;

    public Occurrence(User author, LocalDateTime dateTime, Location location, OccurrenceType type, String description,OriginType originType) {
        this.author = author;
        this.dateTime = dateTime;
        this.location = location;
        this.type = type;
        this.description = description;
        this.originType = originType;
    }
}
