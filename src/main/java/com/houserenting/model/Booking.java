package com.houserenting.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double total;
    private String status;
    private LocalDateTime create_at;
    @ManyToOne
    private House house;
    @ManyToOne
    private User user;
}
