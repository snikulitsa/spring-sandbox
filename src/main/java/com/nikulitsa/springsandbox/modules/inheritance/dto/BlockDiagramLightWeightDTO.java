package com.nikulitsa.springsandbox.modules.inheritance.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagramStatus;
import com.nikulitsa.springsandbox.utils.InstantDeserializer;
import com.nikulitsa.springsandbox.utils.InstantSerializer;

import java.time.Instant;

/**
 * @author Sergey Nikulitsa
 */
public class BlockDiagramLightWeightDTO {

    private Long id;

    private BlockDiagramStatus status;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant creationDate;

    public BlockDiagramLightWeightDTO() {
    }

    public BlockDiagramLightWeightDTO(Long id, BlockDiagramStatus status, Instant creationDate) {
        this.id = id;
        this.status = status;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public BlockDiagramLightWeightDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public BlockDiagramStatus getStatus() {
        return status;
    }

    public BlockDiagramLightWeightDTO setStatus(BlockDiagramStatus status) {
        this.status = status;
        return this;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public BlockDiagramLightWeightDTO setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
