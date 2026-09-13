package com.jantsee.idopontfoglalo.naptar.munkavallalo;

import com.jantsee.idopontfoglalo.common.exception.BusinessRuleException;
import com.jantsee.idopontfoglalo.common.exception.ResourceNotFoundException;
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
            throw new BusinessRuleException("A név nem lehet üres vagy null");
        }
        return munkavallaloRepository.save(uj);
    }

    public List<MunkavallaloEntity> osszesLekerese() {
        return munkavallaloRepository.findAll();
    }

    public MunkavallaloEntity lekeresIdAlapjan(Long id) {
        return munkavallaloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nincs ilyen munkavállaló: " + id));
    }

    public MunkavallaloEntity frissites(Long id, MunkavallaloEntity modositott) {
        MunkavallaloEntity letezo = lekeresIdAlapjan(id);
        if (modositott.getNev() == null || modositott.getNev().isBlank()) {
            throw new BusinessRuleException("A név nem lehet üres vagy null");
        }
        letezo.setNev(modositott.getNev());
        return munkavallaloRepository.save(letezo);
    }

    public void torles(Long id) {
        if (!munkavallaloRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nincs ilyen munkavállaló: " + id);
        }
        munkavallaloRepository.deleteById(id);
    }
}