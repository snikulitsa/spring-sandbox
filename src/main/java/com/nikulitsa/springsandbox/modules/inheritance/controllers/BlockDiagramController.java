package com.nikulitsa.springsandbox.modules.inheritance.controllers;

import com.nikulitsa.springsandbox.modules.inheritance.entities.BlockDiagram;
import com.nikulitsa.springsandbox.modules.inheritance.services.BlockDiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    //    @PostMapping
    //    public BlockDiagramDTO save(@RequestBody BlockDiagramDTO dto) {
    //        return service.save(dto);
    //    }
    //
    //    @GetMapping
    //    public BlockDiagramDTO get(@RequestParam Long id) {
    //        return service.get(id);
    //    }

    @PostMapping
    public BlockDiagram save(@RequestBody BlockDiagram blockDiagram) {
        return service.save(blockDiagram);
    }

    @GetMapping
    public BlockDiagram get(@RequestParam Long id) {
        return service.get(id);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        service.delete(id);
    }
}
