package com.learn.jd.dto;

import com.learn.jd.entity.Guest;

public class GuestResponse {

    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String idProof;

    public GuestResponse(Guest guest) {
        this.id = guest.getId();
        this.fullName = guest.getFullName();
        this.email = guest.getEmail();
        this.phone = guest.getPhone();
        this.idProof = guest.getIdProof();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdProof() {
        return idProof;
    }
}
