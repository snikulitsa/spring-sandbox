package com.nikulitsa.springsandbox.modules.inheritance.repositories;

import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Nikulitsa
 */
@Repository
public interface BlockDiagramRepository extends JpaRepository<BlockDiagram, Long> {
}
