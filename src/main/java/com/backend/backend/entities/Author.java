package com.backend.backend.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Author")
@Table(name = "Author")
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    
    @ManyToOne(targetEntity = Article.class)
    @JoinColumn(name = "ref_article", referencedColumnName = "id" , nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Article article;
    
    @ManyToOne(targetEntity = Researcher.class)
    @JoinColumn(name = "ref_researcher", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Researcher researcher;
    
}
