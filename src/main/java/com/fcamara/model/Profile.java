package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_profile", schema = "fixyou")
@SequenceGenerator(name = "tb_profile_seq", allocationSize = 1)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_profile_seq")
    private Long id;

    private String unit;
    private String department;
    private String preferredShift;
    private String jobTitle;
    private String cpf;
    private String cnpj;
    private String rg;
    private String cnh;

    public Profile(Long id, String unit, String department, String preferredShift, String jobTitle, String cpf, String cnpj, String rg, String cnh) {
        this.id = id;
        this.unit = unit;
        this.department = department;
        this.preferredShift = preferredShift;
        this.jobTitle = jobTitle;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.rg = rg;
        this.cnh = cnh;
    }

    public Profile(Long id, String unit, String department, String preferredShift, String jobTitle) {
        this.id = id;
        this.unit = unit;
        this.department = department;
        this.preferredShift = preferredShift;
        this.jobTitle = jobTitle;
    }

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPreferredShift() {
        return preferredShift;
    }

    public void setPreferredShift(String preferredShift) {
        this.preferredShift = preferredShift;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", unit='" + unit + '\'' +
                ", department='" + department + '\'' +
                ", preferredShift='" + preferredShift + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", rg='" + rg + '\'' +
                ", cnh='" + cnh + '\'' +
                '}';
    }
}
