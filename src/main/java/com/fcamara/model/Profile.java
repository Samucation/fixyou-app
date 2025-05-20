package com.fcamara.model;

import jakarta.persistence.*;


@Entity
@Table(name = "tb_profile", schema = "amicred")
@SequenceGenerator(name = "tb_profile_seq", allocationSize = 1)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_profile_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ProfileType type;

    public Profile(ProfileType type) {
        this.type = type;
    }

    public Profile(Long id, ProfileType type) {
        this.id = id;
        this.type = type;
    }

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileType getType() {
        return type;
    }

    public void setType(ProfileType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
