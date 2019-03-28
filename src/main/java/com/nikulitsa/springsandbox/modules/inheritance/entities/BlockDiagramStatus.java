package com.nikulitsa.springsandbox.modules.inheritance.entities;

/**
 * @author Sergey Nikulitsa
 */
public enum BlockDiagramStatus {

    /**
     * Активная актуальная версия.
     */
    ACTIVE,

    /**
     * Удаленная актуальная версия.
     */
    DELETED,

    /**
     * Версия.
     */
    VERSION,

    /**
     * Статус для версии, который проставляется, когда удаляется актуальная версия.
     */
    DELETED_VERSION,

    /**
     * Статус для версии, который проставляется, когда удаляется именно эта версия.
     */
    DELETED_VERSION_BY_ITSELF
}
