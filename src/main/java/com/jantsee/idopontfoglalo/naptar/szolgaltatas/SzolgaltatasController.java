package com.jantsee.idopontfoglalo.naptar.szolgaltatas;

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
@RequestMapping("/api/szolgaltatasok")
public class SzolgaltatasController {

    private final SzolgaltatasService szolgaltatasService;

    public SzolgaltatasController(SzolgaltatasService szolgaltatasService) {
        this.szolgaltatasService = szolgaltatasService;
    }

    @PostMapping
    public SzolgaltatasEntity letrehozas(@RequestBody SzolgaltatasEntity uj) {
        return szolgaltatasService.letrehozas(uj);
    }

    @GetMapping
    public List<SzolgaltatasEntity> osszes() {
        return szolgaltatasService.osszesLekerese();
    }

    @GetMapping("/{id}")
    public SzolgaltatasEntity egyLekerese(@PathVariable Long id) {
        return szolgaltatasService.lekeresIdAlapjan(id);
    }

    @PutMapping("/{id}")
    public SzolgaltatasEntity frissites(@PathVariable Long id, @RequestBody SzolgaltatasEntity modositott) {
        return szolgaltatasService.frissites(id, modositott);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> torles(@PathVariable Long id) {
        szolgaltatasService.torles(id);
        return ResponseEntity.noContent().build();
    }
}