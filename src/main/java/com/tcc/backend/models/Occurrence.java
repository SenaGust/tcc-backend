package com.tcc.backend.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.tcc.backend.models.User;

@Entity
public class Occurrence {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;
    private LocalDateTime dateTime;
    @ManyToOne
    private Location location;
    @ManyToOne
    private OccurrenceType type;
    private String description;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public OccurrenceType getType() {
        return type;
    }
    public void setType(OccurrenceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
}
