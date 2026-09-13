package com.jantsee.idopontfoglalo.naptar.szolgaltatas;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SzolgaltatasService {
    private final SzolgaltatasRepository szolgaltatasRepository;

    public SzolgaltatasService(SzolgaltatasRepository szolgaltatasRepository) {
        this.szolgaltatasRepository = szolgaltatasRepository;
    }

    public SzolgaltatasEntity letrehozas(SzolgaltatasEntity uj) {
        if (uj.getAr() == null || uj.getAr() <= 0) {
            throw new IllegalArgumentException("Az ár nem lehet 0 vagy negatív");
        }
        return szolgaltatasRepository.save(uj);
    }

    public List<SzolgaltatasEntity> osszesLekerese() {
        return szolgaltatasRepository.findAll();
    }

    public SzolgaltatasEntity lekeresIdAlapjan(Long id) {
        return szolgaltatasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen szolgáltatás: " + id));
    }

    public SzolgaltatasEntity frissites(Long id, SzolgaltatasEntity modositott) {
        SzolgaltatasEntity letezo = lekeresIdAlapjan(id);
        if (modositott.getAr() == null || modositott.getAr() <= 0) {
            throw new IllegalArgumentException("Az ár nem lehet 0 vagy negatív");
        }
        if (modositott.getNev() == null || modositott.getNev().isBlank()) {
            throw new IllegalArgumentException("A név nem lehet üres vagy null");
        }
        if (modositott.getIdotartamPerc() == null || modositott.getIdotartamPerc() <= 0) {
            throw new IllegalArgumentException("Az időtartam nem lehet 0 vagy negatív");
        }
        letezo.setNev(modositott.getNev());
        letezo.setAr(modositott.getAr());
        letezo.setIdotartamPerc(modositott.getIdotartamPerc());
        return szolgaltatasRepository.save(letezo);
    }

    public void torles(Long id) {
        if (!szolgaltatasRepository.existsById(id)) {
            throw new IllegalArgumentException("Nincs ilyen szolgáltatás: " + id);
        }
        szolgaltatasRepository.deleteById(id);
    }
}