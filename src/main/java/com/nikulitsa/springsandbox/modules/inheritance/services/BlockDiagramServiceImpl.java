package com.nikulitsa.springsandbox.modules.inheritance.services;

import com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO;
import com.nikulitsa.springsandbox.modules.inheritance.entities.ActionFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BaseFigure;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagramStatus;
import com.nikulitsa.springsandbox.modules.inheritance.entities.LinkFigure;
import com.nikulitsa.springsandbox.modules.inheritance.repositories.BlockDiagramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.nikulitsa.springsandbox.utils.ExceptionFactory.entityNotFoundExceptionSupplier;
import static com.nikulitsa.springsandbox.utils.ExceptionFactory.idMustBeNull;
import static com.nikulitsa.springsandbox.utils.ExceptionFactory.originIdMustNotBeNull;

/**
 * @author Sergey Nikulitsa
 */
@Service
public class BlockDiagramServiceImpl implements BlockDiagramService {

    private static final Collection<BlockDiagramStatus> CURRENT_AND_VERSIONS =
        new ArrayList<BlockDiagramStatus>() {{
            add(BlockDiagramStatus.ACTIVE);
            add(BlockDiagramStatus.VERSION);
        }};

    private static final Collection<BlockDiagramStatus> DELETED_AND_DELETED_VERSIONS =
        new ArrayList<BlockDiagramStatus>() {{
            add(BlockDiagramStatus.DELETED);
            add(BlockDiagramStatus.DELETED_VERSION);
        }};

    private final BlockDiagramRepository repository;

    @Autowired
    public BlockDiagramServiceImpl(BlockDiagramRepository repository) {
        this.repository = repository;
    }

    @Override
    public BlockDiagram save(BlockDiagram blockDiagram) {
        if (blockDiagram.getId() != null) {
            throw idMustBeNull(BlockDiagram.class);
        }
        blockDiagram.setStatus(BlockDiagramStatus.ACTIVE);
        blockDiagram.setCreationDate(Instant.now());
        BlockDiagram saved = repository.save(blockDiagram);
        saved.setOriginId(saved.getId());
        return repository.save(saved);
    }

    @Transactional
    @Override
    public BlockDiagram edit(BlockDiagram newVersion) {
        Long originId = newVersion.getOriginId();
        if (originId == null) {
            throw originIdMustNotBeNull(BlockDiagram.class);
        }
        BlockDiagram currentVersion = get(originId);
        currentVersion.setStatus(BlockDiagramStatus.VERSION);
        repository.save(currentVersion);
        // ID должны быть вычищены во всех вложенных сущностях рекурсивно.
        clearAllIds(newVersion);
        newVersion.setCreationDate(Instant.now());
        newVersion.setStatus(BlockDiagramStatus.ACTIVE);
        return repository.save(newVersion);
    }

    private void clearAllIds(BlockDiagram newVersion) {
        newVersion.clearId();
        newVersion.getActionFigures().forEach(ActionFigure::clearId);
        newVersion.getBeginFigures().forEach(BaseFigure::clearId);
        newVersion.getConditionFigures().forEach(BaseFigure::clearId);
        newVersion.getLinkFigures().forEach(LinkFigure::clearId);
    }

    @Transactional
    @Override
    public void delete(Long originId) {
        // Достаем текущую версию и версии.
        Collection<BlockDiagram> blockDiagrams = repository
            .findAllByOriginIdAndStatusIn(originId, CURRENT_AND_VERSIONS);
        // Проставляем соответствующие статусы.
        blockDiagrams.forEach(blockDiagram -> {
            BlockDiagramStatus status = blockDiagram.getStatus();
            switch (status) {
                case ACTIVE:
                    blockDiagram.setStatus(BlockDiagramStatus.DELETED);
                    break;
                case VERSION:
                    blockDiagram.setStatus(BlockDiagramStatus.DELETED_VERSION);
                    break;
                default:
                    break;
            }
        });
        repository.saveAll(blockDiagrams);
    }

    @Transactional
    @Override
    public BlockDiagram restore(Long originId) {
        // Достаем удаленную актуальную версию и её версии, которые не удалены сами по себе.
        Collection<BlockDiagram> blockDiagrams = repository
            .findAllByOriginIdAndStatusIn(originId, DELETED_AND_DELETED_VERSIONS);
        // Проставляем соответствующие статусы.
        blockDiagrams.forEach(blockDiagram -> {
            BlockDiagramStatus status = blockDiagram.getStatus();
            switch (status) {
                case DELETED:
                    blockDiagram.setStatus(BlockDiagramStatus.ACTIVE);
                    break;
                case DELETED_VERSION:
                    blockDiagram.setStatus(BlockDiagramStatus.VERSION);
                    break;
                default:
                    break;
            }
        });
        repository.saveAll(blockDiagrams);
        return get(originId);
    }

    @Transactional
    @Override
    public void deleteVersion(Long versionId) {
        BlockDiagram versionToDelete = repository.findByIdAndStatus(versionId, BlockDiagramStatus.VERSION)
            .orElseThrow(entityNotFoundExceptionSupplier(BlockDiagram.class, versionId));
        versionToDelete.setStatus(BlockDiagramStatus.DELETED_VERSION_BY_ITSELF);
        repository.save(versionToDelete);
    }

    @Transactional
    @Override
    public BlockDiagram restoreVersion(Long versionId) {
        BlockDiagram versionToRestore = repository
            .findByIdAndStatus(versionId, BlockDiagramStatus.DELETED_VERSION_BY_ITSELF)
            .orElseThrow(entityNotFoundExceptionSupplier(BlockDiagram.class, versionId));
        versionToRestore.setStatus(BlockDiagramStatus.VERSION);
        return repository.save(versionToRestore);
    }

    @Transactional
    @Override
    public BlockDiagram get(Long originId) {
        return repository.findByOriginIdAndStatus(originId, BlockDiagramStatus.ACTIVE)
            .orElseThrow(entityNotFoundExceptionSupplier(BlockDiagram.class, originId));
    }

    @Transactional
    @Override
    public Page<BlockDiagram> getAllActive(int page, int pageSize) {
        return repository.getPageByStatus(BlockDiagramStatus.ACTIVE, PageRequest.of(page, pageSize));
    }

    @Transactional
    @Override
    public Page<BlockDiagram> getAllDeleted(int page, int pageSize) {
        return repository.getPageByStatus(BlockDiagramStatus.DELETED, PageRequest.of(page, pageSize));
    }

    @Override
    public Collection<BlockDiagramLightWeightDTO> getVersions(Long originId) {
        return repository.findAllByOriginIdAndStatusInLightWeight(originId, CURRENT_AND_VERSIONS);
    }

    @Override
    public Collection<BlockDiagramLightWeightDTO> getDeletedVersions(Long originId) {
        return repository.findAllByOriginIdAndStatusLightWeight(originId, BlockDiagramStatus.DELETED_VERSION_BY_ITSELF);
    }
}
