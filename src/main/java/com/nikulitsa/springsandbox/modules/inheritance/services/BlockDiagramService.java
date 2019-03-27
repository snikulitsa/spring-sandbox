package com.nikulitsa.springsandbox.modules.inheritance.services;

import com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramDTO;
import com.nikulitsa.springsandbox.modules.inheritance.entities.ActionFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BaseFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BeginFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.entities.LinkFigure;
import com.nikulitsa.springsandbox.modules.inheritance.repositories.BlockDiagramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Nikulitsa
 */
@Service
public class BlockDiagramService {

    private final BlockDiagramRepository blockDiagramRepository;

    @Autowired
    public BlockDiagramService(BlockDiagramRepository blockDiagramRepository) {
        this.blockDiagramRepository = blockDiagramRepository;
    }

    //    public BlockDiagramDTO save(BlockDiagramDTO dto) {
    //        return new BlockDiagramDTO(
    //            blockDiagramRepository.save(fromDTO(dto))
    //        );
    //    }

    public BlockDiagram save(BlockDiagram blockDiagram) {
        return blockDiagramRepository.save(blockDiagram);
    }

    public void delete(Long id) {
        blockDiagramRepository.deleteById(id);
    }

    //    public BlockDiagramDTO get(Long id) {
    //        BlockDiagram blockDiagram = blockDiagramRepository.findById(id)
    //            .orElseThrow(() -> new EntityNotFoundException("Block Diagram with id=" + id + " not found."));
    //        return new BlockDiagramDTO(blockDiagram);
    //    }

    public BlockDiagram get(Long id) {
        return blockDiagramRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Block Diagram with id=" + id + " not found."));
    }

    private BlockDiagram fromDTO(BlockDiagramDTO blockDiagramDTO) {
        return new BlockDiagram()
            .setId(blockDiagramDTO.getId())
            .setMarkup(blockDiagramDTO.getMarkup());
        //.setFigures(fillFiguresList(blockDiagramDTO));
    }

    private List<BaseFigure> fillFiguresList(BlockDiagramDTO blockDiagramDTO) {
        List<BeginFigure> beginFigures = blockDiagramDTO.getBeginFigures();
        List<ActionFigure> actionFigures = blockDiagramDTO.getActionFigures();
        List<LinkFigure> linkFigures = blockDiagramDTO.getLinkFigures();
        ArrayList<BaseFigure> figures = new ArrayList<>(
            beginFigures.size() + actionFigures.size() + linkFigures.size()
        );
        figures.addAll(beginFigures);
        figures.addAll(actionFigures);
        figures.addAll(linkFigures);
        return figures;
    }
}
