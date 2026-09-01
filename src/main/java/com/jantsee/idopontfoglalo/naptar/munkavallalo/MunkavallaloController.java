package com.jantsee.idopontfoglalo.naptar.munkavallalo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/munkavallalok")
public class MunkavallaloController {
    private final MunkavallaloService munkavallaloService;

    public MunkavallaloController(MunkavallaloService munkavallaloService) {
        this.munkavallaloService = munkavallaloService;
    }
    @PostMapping
    public MunkavallaloEntity letrehozas(@RequestBody MunkavallaloEntity uj){
        return munkavallaloService.letrehozas(uj);
    }
    @GetMapping
    public List<MunkavallaloEntity> osszes(){ return munkavallaloService.osszesLekerese();}

    @GetMapping("/{id}")
    public MunkavallaloEntity egyLekerese(@PathVariable Long id){ return munkavallaloService.lekeresIdAlapjan(id);}
}
