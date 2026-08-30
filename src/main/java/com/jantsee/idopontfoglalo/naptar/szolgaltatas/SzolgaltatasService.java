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
}
