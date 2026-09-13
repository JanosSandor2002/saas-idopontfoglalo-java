package com.jantsee.idopontfoglalo.naptar.foglalas;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoglalasService {
    private final FoglalasRepository foglalasRepository;

    public FoglalasService(FoglalasRepository foglalasRepository) {
        this.foglalasRepository = foglalasRepository;
    }

    public FoglalasEntity letrehozas(FoglalasEntity uj) {
        if (uj.getSzolgaltatas() == null) {
            throw new IllegalArgumentException("A szolgáltatás megadása kötelező");
        }
        if (uj.getMunkavallalo() == null) {
            throw new IllegalArgumentException("A munkavállaló megadása kötelező");
        }
        if (uj.getIdopont() == null) {
            throw new IllegalArgumentException("Az időpont megadása kötelező");
        }
        if (uj.getIdopont().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Az időpont nem lehet a múltban");
        }
        if (uj.getUgyfelNev() == null || uj.getUgyfelNev().isBlank()) {
            throw new IllegalArgumentException("Az ügyfél neve nem lehet üres vagy null");
        }
        if (uj.getUgyfelEmail() == null || uj.getUgyfelEmail().isBlank()) {
            throw new IllegalArgumentException("Az ügyfél e-mail címe nem lehet üres vagy null");
        }
        uj.setStatusz(FoglalasStatusz.FOGLALT);
        return foglalasRepository.save(uj);
    }

    public List<FoglalasEntity> osszesLekerese() {
        return foglalasRepository.findAll();
    }

    public FoglalasEntity lekeresIdAlapjan(Long id) {
        return foglalasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nincs ilyen foglalás: " + id));
    }

    public FoglalasEntity frissites(Long id, FoglalasEntity modositott) {
        FoglalasEntity letezo = lekeresIdAlapjan(id);
        if (modositott.getIdopont() == null) {
            throw new IllegalArgumentException("Az időpont megadása kötelező");
        }
        if (modositott.getIdopont().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Az időpont nem lehet a múltban");
        }
        letezo.setIdopont(modositott.getIdopont());
        if (modositott.getMunkavallalo() != null) {
            letezo.setMunkavallalo(modositott.getMunkavallalo());
        }
        if (modositott.getSzolgaltatas() != null) {
            letezo.setSzolgaltatas(modositott.getSzolgaltatas());
        }
        return foglalasRepository.save(letezo);
    }

    public void torles(Long id) {
        FoglalasEntity letezo = lekeresIdAlapjan(id);
        letezo.setStatusz(FoglalasStatusz.LEMONDVA);
        foglalasRepository.save(letezo);
    }
}