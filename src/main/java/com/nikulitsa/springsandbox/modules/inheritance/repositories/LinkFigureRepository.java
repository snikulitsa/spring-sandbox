package com.nikulitsa.springsandbox.modules.inheritance.repositories;

import com.nikulitsa.springsandbox.modules.inheritance.entities.LinkFigure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Nikulitsa
 */
@Repository
public interface LinkFigureRepository extends JpaRepository<LinkFigure, Long> {
}
