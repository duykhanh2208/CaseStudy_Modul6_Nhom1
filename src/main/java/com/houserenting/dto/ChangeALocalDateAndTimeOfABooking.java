package com.houserenting.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChangeALocalDateAndTimeOfABooking {
    private Long id;
    private LocalDateTime localDateTime;
}
