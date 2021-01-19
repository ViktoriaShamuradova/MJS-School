package com.epam.esm.dto;

import com.epam.esm.entity.Certificate;

import java.util.List;

public class TagDTO {
    private int id;
    private String name;
    private List<CertificateDTO> certificates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CertificateDTO> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<CertificateDTO> certificates) {
        this.certificates = certificates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
