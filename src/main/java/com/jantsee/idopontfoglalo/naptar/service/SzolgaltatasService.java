package com.jantsee.idopontfoglalo.naptar.service;


import com.jantsee.idopontfoglalo.naptar.entity.Szolgaltatas;
import com.jantsee.idopontfoglalo.naptar.repository.SzolgaltatasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SzolgaltatasService {
    private final SzolgaltatasRepository szolgaltatasRepository;

    public SzolgaltatasService(SzolgaltatasRepository szolgaltatasRepository) {
        this.szolgaltatasRepository = szolgaltatasRepository;
    }

    public Szolgaltatas letrehozas(Szolgaltatas uj) {
        if (uj.getAr() == null || uj.getAr() <= 0) {
            throw new IllegalArgumentException("Az ár nem lehet 0 vagy negatív");
        }
        return szolgaltatasRepository.save(uj);
    }

    public List<Szolgaltatas> osszesLekerese() {
        return szolgaltatasRepository.findAll();
    }

    public Szolgaltatas lekeresIdAlapjan(Long id) {
        return szolgaltatasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen szolgáltatás: " + id));
    }
}
