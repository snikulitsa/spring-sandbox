package com.nikulitsa.springsandbox.modules.inheritance.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@DiscriminatorValue(value = FigureType.Values.BEGIN)
public class BeginFigure extends BaseFigure {

    @Transient
    private final FigureType figureType = FigureType.BEGIN;

    @Column(name = "linkToNextElement")
    private String linkToNextElement;

    public String getLinkToNextElement() {
        return linkToNextElement;
    }

    public BeginFigure setLinkToNextElement(String linkToNextElement) {
        this.linkToNextElement = linkToNextElement;
        return this;
    }

    @Override
    public FigureType getFigureType() {
        return figureType;
    }
}
