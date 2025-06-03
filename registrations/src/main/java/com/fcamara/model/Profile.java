package com.fcamara.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_profile", schema = "fixyou")
@SequenceGenerator(name = "tb_profile_seq", allocationSize = 1)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_profile_seq")
    private Long id;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private PersonData personData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToMany
    @JoinTable(
            name = "tb_profile_departments",
            schema = "fixyou",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Set<Department> departments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tb_profile_sectors",
            schema = "fixyou",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private Set<Sector> sectors = new HashSet<>();

    private String preferredShift;
    private String jobTitle;

    public Profile() {}

    public Profile(Long id, Unit unit, Set<Department> departments, String preferredShift, String jobTitle) {
        this.id = id;
        this.unit = unit;
        this.departments = departments;
        this.preferredShift = preferredShift;
        this.jobTitle = jobTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
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
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", unit=" + (unit != null ? unit.getId() : null) +
                ", preferredShift='" + preferredShift + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", departments=" + departments +
                '}';
    }

    public static final class Builder {
        private Long id;
        private PersonData personData;
        private Unit unit;
        private Set<Department> departments;
        private Set<Sector> sectors;
        private String preferredShift;
        private String jobTitle;

        public Builder() {
        }

        public Builder(Profile other) {
            this.id = other.id;
            this.personData = other.personData;
            this.unit = other.unit;
            this.departments = other.departments;
            this.sectors = other.sectors;
            this.preferredShift = other.preferredShift;
            this.jobTitle = other.jobTitle;
        }

        public static Builder aProfile() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder personData(PersonData personData) {
            this.personData = personData;
            return this;
        }

        public Builder unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public Builder departments(Set<Department> departments) {
            this.departments = departments;
            return this;
        }

        public Builder sectors(Set<Sector> sectors) {
            this.sectors = sectors;
            return this;
        }

        public Builder preferredShift(String preferredShift) {
            this.preferredShift = preferredShift;
            return this;
        }

        public Builder jobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
            return this;
        }

        public Profile build() {
            Profile profile = new Profile();
            profile.setId(id);
            profile.setPersonData(personData);
            profile.setUnit(unit);
            profile.setDepartments(departments);
            profile.setPreferredShift(preferredShift);
            profile.setJobTitle(jobTitle);
            profile.sectors = this.sectors;
            return profile;
        }
    }
}
