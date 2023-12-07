package com.houserenting.dto;

import lombok.Data;

@Data
public class ChangePasswordInfo {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
