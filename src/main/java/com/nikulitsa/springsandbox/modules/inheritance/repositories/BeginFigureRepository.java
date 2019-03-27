package com.nikulitsa.springsandbox.modules.inheritance.repositories;

import com.nikulitsa.springsandbox.modules.inheritance.entities.BeginFigure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Nikulitsa
 */
@Repository
public interface BeginFigureRepository extends JpaRepository<BeginFigure, Long> {
}
