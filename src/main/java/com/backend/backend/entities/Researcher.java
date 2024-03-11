package com.backend.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This class is utilized to construct the Researcher entity,
 * which holds the information of researchers within the database.
 * Annotated as an entity with the table name "Researcher",
 * the class attributes correspond to the columns of the table.
 * Lombok is employed to automatically generate the getters, setters,
 * and constructors. Furthermore, JPA annotations are utilized to
 * automatically generate the table and its columns within the database.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Researcher")
@Table(name = "Researcher")
public class Researcher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    
    @Column(name = "surname", nullable = false, length = 64)
    private String surname;
    
    @Column(name = "secSurname", nullable = false, length = 64)
    private String secSurname;
    
    @Column(name = "university", nullable = false, length = 64)
    private String university;
}
