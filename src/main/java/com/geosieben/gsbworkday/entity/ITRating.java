package com.geosieben.gsbworkday.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="itrating")
public class ITRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ticketid")
    private ItTicket itTicket;
    private int rating;
    private String ratingDescription;
    private LocalDateTime createdAt;
    public ITRating(ItTicket itTicket, int rating, String ratingDescription, LocalDateTime createdAt) {
        this.itTicket = itTicket;
        this.rating = rating;
        this.ratingDescription = ratingDescription;
        this.createdAt = createdAt;
    }
    
}
