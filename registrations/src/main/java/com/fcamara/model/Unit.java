package com.fcamara.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_unit", schema = "fixyou")
@SequenceGenerator(name = "tb_unit_seq", allocationSize = 1)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_unit_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String code;      // Internal or external branch code
    private String address;
    private String city;
    private String state;
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    public Unit() {}

    public Unit(Long id, String name, String code, String address, String city, String state, String zipCode, Institution company) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.institution = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;
        Unit branch = (Unit) o;
        return Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", company=" + (institution != null ? institution.getId() : null) +
                '}';
    }

    public static final class Builder {
        private Unit unity;

        private Builder() {
            unity = new Unit();
        }

        public static Builder anUnity() {
            return new Builder();
        }

        public Builder id(Long id) {
            unity.setId(id);
            return this;
        }

        public Builder name(String name) {
            unity.setName(name);
            return this;
        }

        public Builder code(String code) {
            unity.setCode(code);
            return this;
        }

        public Builder address(String address) {
            unity.setAddress(address);
            return this;
        }

        public Builder city(String city) {
            unity.setCity(city);
            return this;
        }

        public Builder state(String state) {
            unity.setState(state);
            return this;
        }

        public Builder zipCode(String zipCode) {
            unity.setZipCode(zipCode);
            return this;
        }

        public Builder institution(Institution institution) {
            unity.setInstitution(institution);
            return this;
        }

        public Unit build() {
            return unity;
        }
    }
}
