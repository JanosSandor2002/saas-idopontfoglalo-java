package com.jantsee.idopontfoglalo.ugyfel;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UgyfelService {
    private final UgyfelRepository ugyfelRepository;

    public UgyfelService(UgyfelRepository ugyfelRepository) {
        this.ugyfelRepository = ugyfelRepository;
    }

    public UgyfelEntity letrehozas(UgyfelEntity uj) {
        if (uj.getNev() == null || uj.getNev().isBlank()) {
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        if (uj.getEmail() == null || uj.getEmail().isBlank()) {
            throw new IllegalArgumentException("Az e-mail cím nem lehet üres vagy null");
        }
        return ugyfelRepository.save(uj);
    }

    public List<UgyfelEntity> osszesLekerese() {
        return ugyfelRepository.findAll();
    }

    public UgyfelEntity lekeresIdAlapjan(Long id) {
        return ugyfelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen ügyfél: " + id));
    }

    public UgyfelEntity frissites(Long id, UgyfelEntity modositott) {
        UgyfelEntity letezo = lekeresIdAlapjan(id);
        if (modositott.getNev() == null || modositott.getNev().isBlank()) {
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        if (modositott.getEmail() == null || modositott.getEmail().isBlank()) {
            throw new IllegalArgumentException("Az e-mail cím nem lehet üres vagy null");
        }
        letezo.setNev(modositott.getNev());
        letezo.setEmail(modositott.getEmail());
        letezo.setTelefon(modositott.getTelefon());
        return ugyfelRepository.save(letezo);
    }

    public void torles(Long id) {
        if (!ugyfelRepository.existsById(id)) {
            throw new IllegalArgumentException("Nincs ilyen ügyfél: " + id);
        }
        ugyfelRepository.deleteById(id);
    }
}