package com.nikulitsa.springsandbox.modules.inheritance.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@DiscriminatorValue(value = FigureType.Values.ACTION)
public class ActionFigure extends BaseFigure {

    @Transient
    private final FigureType figureType = FigureType.ACTION;

    @Column(name = "linkToNextElement")
    private String linkToNextElement;

    @Override
    public FigureType getFigureType() {
        return figureType;
    }
}
