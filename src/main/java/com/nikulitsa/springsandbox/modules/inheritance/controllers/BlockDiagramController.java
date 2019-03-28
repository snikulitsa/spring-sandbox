package com.nikulitsa.springsandbox.modules.inheritance.controllers;

import com.nikulitsa.springsandbox.modules.inheritance.dto.BlockDiagramLightWeightDTO;
import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.services.BlockDiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/blockDiagram")
public class BlockDiagramController {

    private final BlockDiagramService service;

    @Autowired
    public BlockDiagramController(BlockDiagramService service) {
        this.service = service;
    }

    @PostMapping
    public BlockDiagram save(@RequestBody BlockDiagram blockDiagram) {
        return service.save(blockDiagram);
    }

    @GetMapping
    public BlockDiagram get(@RequestParam Long originId) {
        return service.get(originId);
    }

    @GetMapping("/active")
    public Page<BlockDiagram> getAllActive(@RequestParam int page, @RequestParam int pageSize) {
        return service.getAllActive(--page, pageSize);
    }

    @GetMapping("/deleted")
    public Page<BlockDiagram> getAllDeleted(@RequestParam int page, @RequestParam int pageSize) {
        return service.getAllDeleted(--page, pageSize);
    }

    @GetMapping("/versions")
    public Collection<BlockDiagramLightWeightDTO> getVersions(@RequestParam Long originId) {
        return service.getVersions(originId);
    }

    @GetMapping("/versionsDeleted")
    public Collection<BlockDiagramLightWeightDTO> getDeletedVersions(@RequestParam Long originId) {
        return service.getDeletedVersions(originId);
    }

    @PutMapping
    public BlockDiagram edit(@RequestBody BlockDiagram blockDiagram) {
        return service.edit(blockDiagram);
    }

    @DeleteMapping
    public void delete(@RequestParam Long originId) {
        service.delete(originId);
    }

    @DeleteMapping("/deleteVersion")
    public void deleteVersion(@RequestParam Long versionId) {
        service.deleteVersion(versionId);
    }

    @GetMapping("/restoreVersion")
    public BlockDiagram restoreVersion(@RequestParam Long versionId) {
        return service.restoreVersion(versionId);
    }

    @GetMapping("/restore")
    public BlockDiagram restore(@RequestParam Long originId) {
        return service.restore(originId);
    }
}
