package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_user_context_roles", schema = "fixyou")
public class UserContextRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_user_context_roles_seq")
    @SequenceGenerator(name = "tb_user_context_roles_seq", sequenceName = "tb_user_context_roles_seq", schema = "fixyou", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    private Institution institution;

    @ManyToOne
    private Unit unit;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Sector sector;

    public UserContextRole() {
    }

    public UserContextRole(Long id, User user, UserRole role, Institution institution, Unit unit, Department department, Sector sector) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.institution = institution;
        this.unit = unit;
        this.department = department;
        this.sector = sector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserContextRole that = (UserContextRole) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }




    @Override
    public String toString() {
        return "UserContextRole{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                ", institution=" + institution +
                ", unit=" + unit +
                ", department=" + department +
                ", sector=" + sector +
                '}';
    }

    public static final class Builder {
        private Long id;
        private User user;
        private UserRole role;
        private Institution institution;
        private Unit unit;
        private Department department;
        private Sector sector;

        public Builder() {
        }

        public Builder(UserContextRole other) {
            this.id = other.id;
            this.user = other.user;
            this.role = other.role;
            this.institution = other.institution;
            this.unit = other.unit;
            this.department = other.department;
            this.sector = other.sector;
        }

        public static Builder anUserContextRole() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder institution(Institution institution) {
            this.institution = institution;
            return this;
        }

        public Builder unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder sector(Sector sector) {
            this.sector = sector;
            return this;
        }

        public UserContextRole build() {
            UserContextRole userContextRole = new UserContextRole();
            userContextRole.setId(id);
            userContextRole.setUser(user);
            userContextRole.setRole(role);
            userContextRole.setInstitution(institution);
            userContextRole.setUnit(unit);
            userContextRole.setDepartment(department);
            userContextRole.setSector(sector);
            return userContextRole;
        }
    }
}
