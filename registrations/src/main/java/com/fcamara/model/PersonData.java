package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_person_data", schema = "fixyou")
@SequenceGenerator(name = "tb_person_data_sec", allocationSize = 1)
public class PersonData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_person_data_sec")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContractType contractType; // CLT ou PJ

    private String cpf;
    private String rg;
    private String cnh;
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;


    public PersonData() {
    }

    public PersonData(Long id, ContractType contractType, String cpf, String rg, String cnh, String cnpj, Profile profile) {
        this.id = id;
        this.contractType = contractType;
        this.cpf = cpf;
        this.rg = rg;
        this.cnh = cnh;
        this.cnpj = cnpj;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonData that = (PersonData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "id=" + id +
                ", contractType=" + contractType +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", cnh='" + cnh + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", profile=" + profile +
                '}';
    }
}