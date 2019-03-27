package com.nikulitsa.springsandbox.modules.inheritance.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@Table(name = "figures")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "figure_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BaseFigure {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title")
    private String title;

    @Column(name = "graphical_id")
    private String graphicalId;

    public Long getId() {
        return id;
    }

    public BaseFigure setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BaseFigure setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getGraphicalId() {
        return graphicalId;
    }

    public BaseFigure setGraphicalId(String graphicalId) {
        this.graphicalId = graphicalId;
        return this;
    }

    public abstract FigureType getFigureType();
}
