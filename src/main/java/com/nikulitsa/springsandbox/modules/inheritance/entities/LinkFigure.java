package com.nikulitsa.springsandbox.modules.inheritance.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@DiscriminatorValue(value = FigureType.Values.LINK)
public class LinkFigure extends BaseFigure {

    @Transient
    private final FigureType figureType = FigureType.LINK;

    private String figureFrom;

    private String figureTo;

    public String getFigureFrom() {
        return figureFrom;
    }

    public LinkFigure setFigureFrom(String figureFrom) {
        this.figureFrom = figureFrom;
        return this;
    }

    public String getFigureTo() {
        return figureTo;
    }

    public LinkFigure setFigureTo(String figureTo) {
        this.figureTo = figureTo;
        return this;
    }

    @Override
    public FigureType getFigureType() {
        return figureType;
    }

}
