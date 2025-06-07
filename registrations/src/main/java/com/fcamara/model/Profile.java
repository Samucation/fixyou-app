package com.fcamara.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "profile", schema = "fixyou")
public class Profile {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID person;

    private String position;
    private String contract;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "term_lgpd")
    private boolean termLgpd;

    @Column(name = "has_admin")
    private boolean hasAdmin;

    @Column(name = "has_mobile")
    private boolean hasMobile;

    @Column(name = "has_manager")
    private boolean hasManager;

    private boolean active;


    public Profile() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPerson() {
        return person;
    }

    public void setPerson(UUID person) {
        this.person = person;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isTermLgpd() {
        return termLgpd;
    }

    public void setTermLgpd(boolean termLgpd) {
        this.termLgpd = termLgpd;
    }

    public boolean isHasAdmin() {
        return hasAdmin;
    }

    public void setHasAdmin(boolean hasAdmin) {
        this.hasAdmin = hasAdmin;
    }

    public boolean isHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(boolean hasMobile) {
        this.hasMobile = hasMobile;
    }

    public boolean isHasManager() {
        return hasManager;
    }

    public void setHasManager(boolean hasManager) {
        this.hasManager = hasManager;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return termLgpd == profile.termLgpd && hasAdmin == profile.hasAdmin && hasMobile == profile.hasMobile && hasManager == profile.hasManager && active == profile.active && Objects.equals(id, profile.id) && Objects.equals(person, profile.person) && Objects.equals(position, profile.position) && Objects.equals(contract, profile.contract) && Objects.equals(startDate, profile.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", person=" + person +
                ", position='" + position + '\'' +
                ", contract='" + contract + '\'' +
                ", startDate=" + startDate +
                ", termLgpd=" + termLgpd +
                ", hasAdmin=" + hasAdmin +
                ", hasMobile=" + hasMobile +
                ", hasManager=" + hasManager +
                ", active=" + active +
                '}';
    }
}
