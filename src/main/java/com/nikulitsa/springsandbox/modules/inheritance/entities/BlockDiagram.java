package com.nikulitsa.springsandbox.modules.inheritance.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergey Nikulitsa
 */
@Entity
@Table(name = "block_diagrams")
public class BlockDiagram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "markup")
    private String markup;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BeginFigure> beginFigures = Collections.emptyList();

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ActionFigure> actionFigures = Collections.emptyList();

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<LinkFigure> linkFigures = Collections.emptyList();

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ConditionFigure> conditionFigures = Collections.emptyList();

    //    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //    private List<BaseFigure> figures = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public BlockDiagram setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMarkup() {
        return markup;
    }

    public BlockDiagram setMarkup(String markup) {
        this.markup = markup;
        return this;
    }

    public List<BeginFigure> getBeginFigures() {
        return beginFigures;
    }

    public BlockDiagram setBeginFigures(List<BeginFigure> beginFigures) {
        this.beginFigures = beginFigures;
        return this;
    }

    public List<ActionFigure> getActionFigures() {
        return actionFigures;
    }

    public BlockDiagram setActionFigures(List<ActionFigure> actionFigures) {
        this.actionFigures = actionFigures;
        return this;
    }

    public List<LinkFigure> getLinkFigures() {
        return linkFigures;
    }

    public BlockDiagram setLinkFigures(List<LinkFigure> linkFigures) {
        this.linkFigures = linkFigures;
        return this;
    }

    public List<ConditionFigure> getConditionFigures() {
        return conditionFigures;
    }

    public BlockDiagram setConditionFigures(List<ConditionFigure> conditionFigures) {
        this.conditionFigures = conditionFigures;
        return this;
    }

    //    public List<BaseFigure> getFigures() {
    //        return figures;
    //    }
    //
    //    public BlockDiagram setFigures(List<BaseFigure> figures) {
    //        this.figures = figures;
    //        return this;
    //    }
}
