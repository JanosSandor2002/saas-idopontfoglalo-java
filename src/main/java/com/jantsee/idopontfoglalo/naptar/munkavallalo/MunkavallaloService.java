package com.jantsee.idopontfoglalo.naptar.munkavallalo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunkavallaloService {
    private final MunkavallaloRepository munkavallaloRepository;

    public MunkavallaloService(MunkavallaloRepository munkavallaloRepository) {
        this.munkavallaloRepository = munkavallaloRepository;
    }

    public MunkavallaloEntity letrehozas(MunkavallaloEntity uj) {
        if (uj.getNev() == null || uj.getNev().isBlank()) {
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        return munkavallaloRepository.save(uj);
    }

    public List<MunkavallaloEntity> osszesLekerese() {
        return munkavallaloRepository.findAll();
    }

    public MunkavallaloEntity lekeresIdAlapjan(Long id) {
        return munkavallaloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen munkavállaló: " + id));
    }

    public MunkavallaloEntity frissites(Long id, MunkavallaloEntity modositott) {
        MunkavallaloEntity letezo = lekeresIdAlapjan(id);
        if (modositott.getNev() == null || modositott.getNev().isBlank()) {
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        letezo.setNev(modositott.getNev());
        return munkavallaloRepository.save(letezo);
    }

    public void torles(Long id) {
        if (!munkavallaloRepository.existsById(id)) {
            throw new IllegalArgumentException("Nincs ilyen munkavállaló: " + id);
        }
        munkavallaloRepository.deleteById(id);
    }
}