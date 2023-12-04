package com.houserenting.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createAt;
    private String message;
    private String navigate;
    private String status;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
}
