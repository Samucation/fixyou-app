package com.fcamara.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_institution", schema = "fixyou")
@SequenceGenerator(name = "tb_institution_seq", allocationSize = 1)
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_institution_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String cnpj;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Institution() {}

    public Institution(Long id, String name, String cnpj, String description) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.description = description;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescription() {
        return description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Institution)) return false;
        Institution company = (Institution) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }




    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String cnpj;
        private String description;
        private User createdBy;

        public Builder() {
        }

        public Builder(Institution other) {
            this.id = other.id;
            this.name = other.name;
            this.cnpj = other.cnpj;
            this.description = other.description;
            this.createdBy = other.createdBy;
        }

        public static Builder anInstitution() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cnpj(String cnpj) {
            this.cnpj = cnpj;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder createdBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Institution build() {
            Institution institution = new Institution();
            institution.setId(id);
            institution.setName(name);
            institution.setCnpj(cnpj);
            institution.setDescription(description);
            institution.setCreatedBy(createdBy);
            return institution;
        }
    }
}
