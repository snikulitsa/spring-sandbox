package com.nikulitsa.springsandbox.modules.inheritance.repositories;

import com.nikulitsa.springsandbox.modules.inheritance.entities.BaseFigure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Nikulitsa
 */
@Repository
public interface BaseFigureRepository extends JpaRepository<BaseFigure, Long> {
}
