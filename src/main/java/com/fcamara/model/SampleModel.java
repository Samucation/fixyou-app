package com.fcamara.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_sample")
@SequenceGenerator(name = "tb_sample_seq", allocationSize = 1)
public class SampleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_sample_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public SampleModel() {}

    public SampleModel(Long id, String name) {
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
}
