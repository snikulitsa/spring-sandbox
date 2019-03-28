package com.nikulitsa.springsandbox.modules.inheritance.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nikulitsa.springsandbox.utils.InstantDeserializer;
import com.nikulitsa.springsandbox.utils.InstantSerializer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
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

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "markup")
    private String markup;

    @Column(name = "creation_date")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant creationDate;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private BlockDiagramStatus status;

    @Column(name = "origin_id")
    private Long originId;

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

    public Long getId() {
        return id;
    }

    public BlockDiagram setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BlockDiagram setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMarkup() {
        return markup;
    }

    public BlockDiagram setMarkup(String markup) {
        this.markup = markup;
        return this;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public BlockDiagram setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public BlockDiagramStatus getStatus() {
        return status;
    }

    public BlockDiagram setStatus(BlockDiagramStatus status) {
        this.status = status;
        return this;
    }

    public Long getOriginId() {
        return originId;
    }

    public BlockDiagram setOriginId(Long originId) {
        this.originId = originId;
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

    public void clearId() {
        this.id = null;
    }
}
