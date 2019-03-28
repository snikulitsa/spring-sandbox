package com.nikulitsa.springsandbox.modules.inheritance.services;

import com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import org.springframework.data.domain.Page;

import java.util.Collection;

/**
 * @author Sergey Nikulitsa
 */
public interface BlockDiagramService {

    BlockDiagram save(BlockDiagram blockDiagram);

    BlockDiagram edit(BlockDiagram blockDiagram);

    void delete(Long originId);

    BlockDiagram restore(Long originId);

    void deleteVersion(Long versionId);

    BlockDiagram restoreVersion(Long versionId);

    BlockDiagram get(Long originId);

    Collection<BlockDiagramLightWeightDTO> getVersions(Long originId);

    Page<BlockDiagram> getAllActive(int page, int pageSize);

    Page<BlockDiagram> getAllDeleted(int page, int pageSize);

    Collection<BlockDiagramLightWeightDTO> getDeletedVersions(Long originId);
}
