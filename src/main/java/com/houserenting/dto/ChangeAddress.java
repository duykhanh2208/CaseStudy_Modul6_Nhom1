package com.houserenting.dto;

import lombok.Data;

@Data

public class ChangeAddress {
    private Long id;
    private String district;
    private String province;
    private String ward;
    private String address;
}
