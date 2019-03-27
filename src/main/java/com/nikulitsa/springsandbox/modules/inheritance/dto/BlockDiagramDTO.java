package com.nikulitsa.springsandbox.modules.inheritance.dto;

import com.nikulitsa.springsandbox.modules.inheritance.entities.ActionFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BaseFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BeginFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.entities.FigureType;
import com.nikulitsa.springsandbox.modules.inheritance.entities.LinkFigure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Nikulitsa
 */
public class BlockDiagramDTO {

    private Long id;

    private String markup;

    private List<BeginFigure> beginFigures = new ArrayList<>();

    private List<ActionFigure> actionFigures = new ArrayList<>();

    private List<LinkFigure> linkFigures = new ArrayList<>();

    public BlockDiagramDTO() {
    }

    public BlockDiagramDTO(BlockDiagram blockDiagram) {
        this.id = blockDiagram.getId();
        this.markup = blockDiagram.getMarkup();
        //blockDiagram.getFigures().forEach(this::fillCollections);
    }

    private void fillCollections(BaseFigure baseFigure) {
        FigureType figureType = baseFigure.getFigureType();
        switch (figureType) {
            case BEGIN:
                beginFigures.add((BeginFigure) baseFigure);
                break;
            case LINK:
                linkFigures.add((LinkFigure) baseFigure);
                break;
            case ACTION:
                actionFigures.add((ActionFigure) baseFigure);
                break;
            default:
                break;
        }
    }

    public Long getId() {
        return id;
    }

    public BlockDiagramDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMarkup() {
        return markup;
    }

    public BlockDiagramDTO setMarkup(String markup) {
        this.markup = markup;
        return this;
    }

    public List<BeginFigure> getBeginFigures() {
        return beginFigures;
    }

    public BlockDiagramDTO setBeginFigures(List<BeginFigure> beginFigures) {
        this.beginFigures = beginFigures;
        return this;
    }

    public List<ActionFigure> getActionFigures() {
        return actionFigures;
    }

    public BlockDiagramDTO setActionFigures(List<ActionFigure> actionFigures) {
        this.actionFigures = actionFigures;
        return this;
    }

    public List<LinkFigure> getLinkFigures() {
        return linkFigures;
    }

    public BlockDiagramDTO setLinkFigures(List<LinkFigure> linkFigures) {
        this.linkFigures = linkFigures;
        return this;
    }
}
