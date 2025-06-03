package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_sector", schema = "fixyou")
@SequenceGenerator(name = "tb_sector_seq", allocationSize = 1)
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_sector_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Sector() {
    }

    public Sector(Long id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(id, sector.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }



    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                '}';
    }

    public static final class Builder {
        private Sector sector;

        private Builder() {
            sector = new Sector();
        }

        public static Builder aSector() {
            return new Builder();
        }

        public Builder id(Long id) {
            sector.setId(id);
            return this;
        }

        public Builder name(String name) {
            sector.setName(name);
            return this;
        }

        public Builder department(Department department) {
            sector.setDepartment(department);
            return this;
        }

        public Sector build() {
            return sector;
        }
    }
}
