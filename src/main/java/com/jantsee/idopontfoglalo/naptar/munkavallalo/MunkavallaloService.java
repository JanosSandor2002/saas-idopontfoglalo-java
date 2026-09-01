package com.jantsee.idopontfoglalo.naptar.munkavallalo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunkavallaloService {
    private final MunkavallaloRepository munkavallaloRepository;

    public MunkavallaloService(MunkavallaloRepository munkavallaloRepository) {
        this.munkavallaloRepository = munkavallaloRepository;
    }

    public MunkavallaloEntity letrehozas (MunkavallaloEntity uj){
        if (uj.getNev() == null || uj.getNev().isBlank()){
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        return munkavallaloRepository.save(uj);
    }
    public List<MunkavallaloEntity> osszesLekerese(){ return munkavallaloRepository.findAll();}

    public MunkavallaloEntity lekeresIdAlapjan(Long id){
        return munkavallaloRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("nincs ilyen munkavállaló: " + id));
    }
}
