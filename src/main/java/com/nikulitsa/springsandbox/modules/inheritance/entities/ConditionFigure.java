package com.nikulitsa.springsandbox.modules.inheritance.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@DiscriminatorValue(value = FigureType.Values.CONDITION)
public class ConditionFigure extends BaseFigure {

    @Transient
    private final FigureType figureType = FigureType.CONDITION;

    private String conditionTrueLink;
    
    private String conditionFalseLink;

    public String getConditionTrueLink() {
        return conditionTrueLink;
    }

    public ConditionFigure setConditionTrueLink(String conditionTrueLink) {
        this.conditionTrueLink = conditionTrueLink;
        return this;
    }

    public String getConditionFalseLink() {
        return conditionFalseLink;
    }

    public ConditionFigure setConditionFalseLink(String conditionFalseLink) {
        this.conditionFalseLink = conditionFalseLink;
        return this;
    }

    @Override
    public FigureType getFigureType() {
        return figureType;
    }
}
