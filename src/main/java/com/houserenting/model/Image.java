package com.houserenting.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String status;
    @ManyToOne
    private House house;
    public Image(String url, House house){
        this.url = url;
        this.house = house;
    }

    public Image(Long id, String url){
        this.url = url;
        this.id = id;
    }
}
