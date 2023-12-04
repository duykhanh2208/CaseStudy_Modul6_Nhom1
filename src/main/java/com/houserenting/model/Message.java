package com.houserenting.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createAt;
    private String message;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    private String status;
}
