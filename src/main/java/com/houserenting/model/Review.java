package com.houserenting.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private String status;
    private int rating;
    private LocalDate createAt;
    @ManyToOne
    private Booking booking;

    public Review() {
    }

    public Review(String comment, String status, int rating, LocalDate createAt, Booking booking) {
        this.comment = comment;
        this.status = status;
        this.rating = rating;
        this.createAt = createAt;
        this.booking = booking;
    }

    public Review(String comment) {
        this.comment = comment;
    }
}
