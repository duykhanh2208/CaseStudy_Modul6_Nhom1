package com.houserenting.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String province;
    private String district;
    private String ward;
    private String houseNumber;
    private int bedroom;
    private int bathroom;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String facility;
    private double price;
    private int sale;
    private double area;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;
    private String status;
    private LocalDate createAt;
    private LocalDate updateAt;
    @ManyToOne
    private User owner;

}
