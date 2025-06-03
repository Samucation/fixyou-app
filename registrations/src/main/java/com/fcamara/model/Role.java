package com.fcamara.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_roles", schema = "fixyou")
@SequenceGenerator(name = "tb_role_seq", allocationSize = 1)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_role_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserRole name;

    public Role() {}

    public Role(UserRole name) {
        this.name = name;
    }

    public Role(Long id, UserRole name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getName() {
        return name;
    }

    public void setName(UserRole name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }




    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    public static final class Builder {
        private Long id;
        private UserRole name;

        public Builder() {
        }

        public Builder(Role other) {
            this.id = other.id;
            this.name = other.name;
        }

        public static Builder aRole() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(UserRole name) {
            this.name = name;
            return this;
        }

        public Role build() {
            Role role = new Role();
            role.setId(id);
            role.setName(name);
            return role;
        }
    }
}
