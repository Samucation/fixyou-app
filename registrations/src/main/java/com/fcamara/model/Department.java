package com.fcamara.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_department", schema = "fixyou")
@SequenceGenerator(name = "tb_department_seq", allocationSize = 1)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_department_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "department_type_id")
    private DepartmentType departmentType;

    public Department() {}

    public Department(Long id, String name, Unit unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
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

    public Unit getBranch() {
        return unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public void setBranch(Unit branch) {
        this.unit = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit=" + unit +
                '}';
    }

    public static final class Builder {
        private Department department;

        private Builder() {
            department = new Department();
        }

        public static Builder aDepartment() {
            return new Builder();
        }

        public Builder id(Long id) {
            department.setId(id);
            return this;
        }

        public Builder name(String name) {
            department.setName(name);
            return this;
        }

        public Builder unit(Unit unit) {
            department.setUnit(unit);
            return this;
        }

        public Builder departmentType(DepartmentType departmentType) {
            department.setDepartmentType(departmentType);
            return this;
        }

        public Department build() {
            return department;
        }
    }


}
