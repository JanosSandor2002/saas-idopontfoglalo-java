package com.jantsee.idopontfoglalo.naptar.controller;

import com.jantsee.idopontfoglalo.naptar.entity.Szolgaltatas;
import com.jantsee.idopontfoglalo.naptar.service.SzolgaltatasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Szolgaltatas letrehozas(@RequestBody Szolgaltatas uj) {
        return szolgaltatasService.letrehozas(uj);
    }

    @GetMapping
    public List<Szolgaltatas> osszes() {
        return szolgaltatasService.osszesLekerese();
    }

    @GetMapping("/{id}")
    public Szolgaltatas egyLekerese(@PathVariable Long id) {
        return szolgaltatasService.lekeresIdAlapjan(id);
    }
}