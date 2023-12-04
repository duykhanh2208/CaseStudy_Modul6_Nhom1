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
}
