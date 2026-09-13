package com.jantsee.idopontfoglalo.ugyfel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ugyfelek")
public class UgyfelController {
    private final UgyfelService ugyfelService;

    public UgyfelController(UgyfelService ugyfelService) {
        this.ugyfelService = ugyfelService;
    }

    @PostMapping
    public UgyfelEntity letrehozas(@RequestBody UgyfelEntity uj) {
        return ugyfelService.letrehozas(uj);
    }

    @GetMapping
    public List<UgyfelEntity> osszes() {
        return ugyfelService.osszesLekerese();
    }

    @GetMapping("/{id}")
    public UgyfelEntity egyLekerese(@PathVariable Long id) {
        return ugyfelService.lekeresIdAlapjan(id);
    }

    @PutMapping("/{id}")
    public UgyfelEntity frissites(@PathVariable Long id, @RequestBody UgyfelEntity modositott) {
        return ugyfelService.frissites(id, modositott);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> torles(@PathVariable Long id) {
        ugyfelService.torles(id);
        return ResponseEntity.noContent().build();
    }
}