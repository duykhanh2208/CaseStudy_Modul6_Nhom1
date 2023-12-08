package com.houserenting.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "userTable")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;
    private boolean enabled = true;
    private String firstname;
    private String lastname;
    private String address;
    private String province;
    private String district;
    private String ward;
    @Column(unique = true)
    private String email;
    private String phone;
    private String avatar;
    private double wallet;
    private String status; // Active/Inactive/WaitConfirming/
    @Column(columnDefinition = "TEXT")
    private String frontside;
    @Column(columnDefinition = "TEXT")
    private String backside;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public User(String username, String password, String confirmPassword, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }

    public User(Long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public User(String username, String password, String confirmPassword, boolean enabled, String firstname, String lastname, String address, String province, String district, String ward, String email, String phone, String avatar, double wallet, String status, String frontside, String backside, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.enabled = enabled;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.wallet = wallet;
        this.status = status;
        this.frontside = frontside;
        this.backside = backside;
        this.roles = roles;
    }
    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
