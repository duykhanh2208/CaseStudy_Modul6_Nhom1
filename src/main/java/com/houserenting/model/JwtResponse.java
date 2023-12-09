package com.houserenting.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String avatar;
    private String firstname;
    private String lastname;
    private String province;
    private String district;
    private String ward;
    private String address;
    private String email;
    private String phone;

    private String frontside;
    private String backside;
    private Collection<? extends GrantedAuthority> roles;


    public JwtResponse(String accessToken, Long id, String username, String avatar,String firstname,String lastname,String province,String district,String ward,String address,String email,String phone,String frontside,String backside, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        this.id = id;
        this.avatar = avatar;
        this.firstname=firstname;
        this.lastname=lastname;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.frontside = frontside;
        this.backside = backside;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFrontside() {
        return frontside;
    }

    public void setFrontside(String frontside) {
        this.frontside = frontside;
    }

    public String getBackside() {
        return backside;
    }

    public void setBackside(String backside) {
        this.backside = backside;
    }
}
