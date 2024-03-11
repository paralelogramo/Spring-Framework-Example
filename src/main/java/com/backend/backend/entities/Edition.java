package com.backend.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing an edition of a conference.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Edition")
@Table(name = "Edition")
public class Edition {

    /*
     * Unique identifier of the edition.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Reference to the conference associated with this edition.
     */
    @ManyToOne
    @JoinColumn(name = "ref_conference", referencedColumnName = "id", nullable = false)
    private Conference conference;

    /**
     * Year of the edition.
     */
    @Column(name = "year", nullable = false)
    private int year;

    /**
     * Date of the edition.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    /**
     * City where the edition took place.
     */
    @Column(name = "city", nullable = false, length = 64)
    private String city;
}
