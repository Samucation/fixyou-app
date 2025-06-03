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

    public static final class Builder {
        private Long id;
        private ContractType contractType;
        private String cpf;
        private String rg;
        private String cnh;
        private String cnpj;
        private Profile profile;

        public Builder() {
        }

        public Builder(PersonData other) {
            this.id = other.id;
            this.contractType = other.contractType;
            this.cpf = other.cpf;
            this.rg = other.rg;
            this.cnh = other.cnh;
            this.cnpj = other.cnpj;
            this.profile = other.profile;
        }

        public static Builder aPersonData() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder contractType(ContractType contractType) {
            this.contractType = contractType;
            return this;
        }

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder rg(String rg) {
            this.rg = rg;
            return this;
        }

        public Builder cnh(String cnh) {
            this.cnh = cnh;
            return this;
        }

        public Builder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        public Builder profile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public PersonData build() {
            PersonData personData = new PersonData();
            personData.setId(id);
            personData.setContractType(contractType);
            personData.setCpf(cpf);
            personData.setRg(rg);
            personData.setCnh(cnh);
            personData.setCnpj(cnpj);
            personData.setProfile(profile);
            return personData;
        }
    }
}