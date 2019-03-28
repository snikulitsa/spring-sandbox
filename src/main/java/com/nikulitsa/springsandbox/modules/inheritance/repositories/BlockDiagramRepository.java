package com.nikulitsa.springsandbox.modules.inheritance.repositories;

import com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagramStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Sergey Nikulitsa
 */
@Repository
public interface BlockDiagramRepository extends JpaRepository<BlockDiagram, Long> {

    @Query("from BlockDiagram where originId=?1 and status=?2")
    Optional<BlockDiagram> findByOriginIdAndStatus(Long originId, BlockDiagramStatus status);

    @Query("from BlockDiagram where originId=?1 and status in ?2")
    Collection<BlockDiagram> findAllByOriginIdAndStatusIn(Long originId, Collection<BlockDiagramStatus> statuses);

    @Query(
        "select new com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO(id, status, creationDate) " +
            "from BlockDiagram where originId=?1 and status in ?2"
    )
    Collection<BlockDiagramLightWeightDTO> findAllByOriginIdAndStatusInLightWeight(
        Long originId,
        Collection<BlockDiagramStatus> statuses
    );

    @Query(
        "select new com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO(id, status, creationDate) " +
            "from BlockDiagram where originId=?1 and status=?2"
    )
    Collection<BlockDiagramLightWeightDTO> findAllByOriginIdAndStatusLightWeight(Long originId, BlockDiagramStatus status);

    @Query("select bd from BlockDiagram bd where bd.status=?1")
    Page<BlockDiagram> getPageByStatus(BlockDiagramStatus active, Pageable pageRequest);

    Optional<BlockDiagram> findByIdAndStatus(Long id, BlockDiagramStatus status);
}
