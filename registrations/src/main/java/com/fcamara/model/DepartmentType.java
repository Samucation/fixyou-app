package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_department_type")
@SequenceGenerator(name = "tb_department_type_seq", allocationSize = 1)
public class DepartmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_department_type_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    public DepartmentType() {
    }

    public DepartmentType(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentType that = (DepartmentType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



    @Override
    public String toString() {
        return "DepartmentType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public static final class Builder {
        private DepartmentType departmentType;

        private Builder() {
            departmentType = new DepartmentType();
        }

        public static Builder aDepartmentType() {
            return new Builder();
        }

        public Builder id(Long id) {
            departmentType.setId(id);
            return this;
        }

        public Builder name(String name) {
            departmentType.setName(name);
            return this;
        }

        public Builder institution(Institution institution) {
            departmentType.setInstitution(institution);
            return this;
        }

        public DepartmentType build() {
            return departmentType;
        }
    }


}

