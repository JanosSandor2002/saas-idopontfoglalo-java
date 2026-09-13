package com.jantsee.idopontfoglalo.naptar.szolgaltatas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SzolgaltatasServiceTest {

    @Mock
    private SzolgaltatasRepository szolgaltatasRepository;

    @InjectMocks
    private SzolgaltatasService szolgaltatasService;

    private SzolgaltatasEntity ervenyesSzolgaltatas() {
        return SzolgaltatasEntity.builder()
                .nev("Hajvágás")
                .idotartamPerc(30)
                .ar(5000)
                .build();
    }

    @Test
    void letrehozas_ervenyesAdatokkal_menti() {
        SzolgaltatasEntity uj = ervenyesSzolgaltatas();
        when(szolgaltatasRepository.save(any(SzolgaltatasEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        SzolgaltatasEntity eredmeny = szolgaltatasService.letrehozas(uj);

        assertThat(eredmeny.getNev()).isEqualTo("Hajvágás");
    }

    @Test
    void letrehozas_nullArral_kivetelt_dob() {
        SzolgaltatasEntity uj = ervenyesSzolgaltatas();
        uj.setAr(null);

        assertThatThrownBy(() -> szolgaltatasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ár nem lehet 0 vagy negatív");
    }

    @Test
    void letrehozas_negativArral_kivetelt_dob() {
        SzolgaltatasEntity uj = ervenyesSzolgaltatas();
        uj.setAr(-100);

        assertThatThrownBy(() -> szolgaltatasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ár nem lehet 0 vagy negatív");
    }

    @Test
    void letrehozas_nullaArral_kivetelt_dob() {
        SzolgaltatasEntity uj = ervenyesSzolgaltatas();
        uj.setAr(0);

        assertThatThrownBy(() -> szolgaltatasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ár nem lehet 0 vagy negatív");
    }

    @Test
    void osszesLekerese_visszaadjaAzOsszesSzolgaltatast() {
        List<SzolgaltatasEntity> lista = List.of(ervenyesSzolgaltatas(), ervenyesSzolgaltatas());
        when(szolgaltatasRepository.findAll()).thenReturn(lista);

        List<SzolgaltatasEntity> eredmeny = szolgaltatasService.osszesLekerese();

        assertThat(eredmeny).hasSize(2);
    }

    @Test
    void lekeresIdAlapjan_letezoId_visszaadjaASzolgaltatast() {
        SzolgaltatasEntity letezo = ervenyesSzolgaltatas();
        letezo.setId(1L);
        when(szolgaltatasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        SzolgaltatasEntity eredmeny = szolgaltatasService.lekeresIdAlapjan(1L);

        assertThat(eredmeny.getId()).isEqualTo(1L);
    }

    @Test
    void lekeresIdAlapjan_nemLetezoId_kivetelt_dob() {
        when(szolgaltatasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> szolgaltatasService.lekeresIdAlapjan(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen szolgáltatás: 99");
    }

    @Test
    void frissites_ervenyesAdatokkal_frissiti() {
        SzolgaltatasEntity letezo = ervenyesSzolgaltatas();
        letezo.setId(1L);
        when(szolgaltatasRepository.findById(1L)).thenReturn(Optional.of(letezo));
        when(szolgaltatasRepository.save(any(SzolgaltatasEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        SzolgaltatasEntity modositott = SzolgaltatasEntity.builder()
                .nev("Hajvágás XL")
                .idotartamPerc(45)
                .ar(6000)
                .build();

        SzolgaltatasEntity eredmeny = szolgaltatasService.frissites(1L, modositott);

        assertThat(eredmeny.getNev()).isEqualTo("Hajvágás XL");
        assertThat(eredmeny.getIdotartamPerc()).isEqualTo(45);
        assertThat(eredmeny.getAr()).isEqualTo(6000);
    }

    @Test
    void frissites_negativArral_kivetelt_dob() {
        SzolgaltatasEntity letezo = ervenyesSzolgaltatas();
        letezo.setId(1L);
        when(szolgaltatasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        SzolgaltatasEntity modositott = SzolgaltatasEntity.builder()
                .nev("Hajvágás XL")
                .idotartamPerc(45)
                .ar(-1)
                .build();

        assertThatThrownBy(() -> szolgaltatasService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ár nem lehet 0 vagy negatív");
    }

    @Test
    void frissites_uresNevvel_kivetelt_dob() {
        SzolgaltatasEntity letezo = ervenyesSzolgaltatas();
        letezo.setId(1L);
        when(szolgaltatasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        SzolgaltatasEntity modositott = SzolgaltatasEntity.builder()
                .nev("  ")
                .idotartamPerc(45)
                .ar(6000)
                .build();

        assertThatThrownBy(() -> szolgaltatasService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void frissites_nullaIdotartammal_kivetelt_dob() {
        SzolgaltatasEntity letezo = ervenyesSzolgaltatas();
        letezo.setId(1L);
        when(szolgaltatasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        SzolgaltatasEntity modositott = SzolgaltatasEntity.builder()
                .nev("Hajvágás XL")
                .idotartamPerc(0)
                .ar(6000)
                .build();

        assertThatThrownBy(() -> szolgaltatasService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az időtartam nem lehet 0 vagy negatív");
    }

    @Test
    void torles_letezoId_meghivjaADeleteById() {
        when(szolgaltatasRepository.existsById(1L)).thenReturn(true);

        szolgaltatasService.torles(1L);

        org.mockito.Mockito.verify(szolgaltatasRepository).deleteById(1L);
    }

    @Test
    void torles_nemLetezoId_kivetelt_dob() {
        when(szolgaltatasRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> szolgaltatasService.torles(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen szolgáltatás: 99");
    }
}