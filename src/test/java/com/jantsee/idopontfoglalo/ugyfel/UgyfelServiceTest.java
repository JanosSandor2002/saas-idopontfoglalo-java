package com.jantsee.idopontfoglalo.ugyfel;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UgyfelServiceTest {

    @Mock
    private UgyfelRepository ugyfelRepository;

    @InjectMocks
    private UgyfelService ugyfelService;

    private UgyfelEntity ervenyesUgyfel() {
        return UgyfelEntity.builder()
                .nev("Teszt Elek")
                .email("teszt@example.com")
                .telefon("+36301234567")
                .build();
    }

    @Test
    void letrehozas_ervenyesAdatokkal_menti() {
        UgyfelEntity uj = ervenyesUgyfel();
        when(ugyfelRepository.save(any(UgyfelEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        UgyfelEntity eredmeny = ugyfelService.letrehozas(uj);

        assertThat(eredmeny.getNev()).isEqualTo("Teszt Elek");
        assertThat(eredmeny.getEmail()).isEqualTo("teszt@example.com");
    }

    @Test
    void letrehozas_nullNevvel_kivetelt_dob() {
        UgyfelEntity uj = ervenyesUgyfel();
        uj.setNev(null);

        assertThatThrownBy(() -> ugyfelService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void letrehozas_uresNevvel_kivetelt_dob() {
        UgyfelEntity uj = ervenyesUgyfel();
        uj.setNev("   ");

        assertThatThrownBy(() -> ugyfelService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void letrehozas_nullEmaillel_kivetelt_dob() {
        UgyfelEntity uj = ervenyesUgyfel();
        uj.setEmail(null);

        assertThatThrownBy(() -> ugyfelService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az e-mail cím nem lehet üres vagy null");
    }

    @Test
    void letrehozas_uresEmaillel_kivetelt_dob() {
        UgyfelEntity uj = ervenyesUgyfel();
        uj.setEmail("  ");

        assertThatThrownBy(() -> ugyfelService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az e-mail cím nem lehet üres vagy null");
    }

    @Test
    void osszesLekerese_visszaadjaAzOsszesUgyfelet() {
        List<UgyfelEntity> lista = List.of(ervenyesUgyfel(), ervenyesUgyfel());
        when(ugyfelRepository.findAll()).thenReturn(lista);

        List<UgyfelEntity> eredmeny = ugyfelService.osszesLekerese();

        assertThat(eredmeny).hasSize(2);
    }

    @Test
    void lekeresIdAlapjan_letezoId_visszaadjaAzUgyfelet() {
        UgyfelEntity letezo = ervenyesUgyfel();
        letezo.setId(1L);
        when(ugyfelRepository.findById(1L)).thenReturn(Optional.of(letezo));

        UgyfelEntity eredmeny = ugyfelService.lekeresIdAlapjan(1L);

        assertThat(eredmeny.getId()).isEqualTo(1L);
    }

    @Test
    void lekeresIdAlapjan_nemLetezoId_kivetelt_dob() {
        when(ugyfelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ugyfelService.lekeresIdAlapjan(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen ügyfél: 99");
    }

    @Test
    void frissites_ervenyesAdatokkal_frissiti() {
        UgyfelEntity letezo = ervenyesUgyfel();
        letezo.setId(1L);
        when(ugyfelRepository.findById(1L)).thenReturn(Optional.of(letezo));
        when(ugyfelRepository.save(any(UgyfelEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        UgyfelEntity modositott = UgyfelEntity.builder()
                .nev("Teszt Elek Módosított")
                .email("modositott@example.com")
                .telefon("+36309999999")
                .build();

        UgyfelEntity eredmeny = ugyfelService.frissites(1L, modositott);

        assertThat(eredmeny.getNev()).isEqualTo("Teszt Elek Módosított");
        assertThat(eredmeny.getEmail()).isEqualTo("modositott@example.com");
        assertThat(eredmeny.getTelefon()).isEqualTo("+36309999999");
    }

    @Test
    void frissites_uresNevvel_kivetelt_dob() {
        UgyfelEntity letezo = ervenyesUgyfel();
        letezo.setId(1L);
        when(ugyfelRepository.findById(1L)).thenReturn(Optional.of(letezo));

        UgyfelEntity modositott = UgyfelEntity.builder()
                .nev("")
                .email("teszt@example.com")
                .build();

        assertThatThrownBy(() -> ugyfelService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void frissites_nullEmaillel_kivetelt_dob() {
        UgyfelEntity letezo = ervenyesUgyfel();
        letezo.setId(1L);
        when(ugyfelRepository.findById(1L)).thenReturn(Optional.of(letezo));

        UgyfelEntity modositott = UgyfelEntity.builder()
                .nev("Teszt Elek")
                .email(null)
                .build();

        assertThatThrownBy(() -> ugyfelService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az e-mail cím nem lehet üres vagy null");
    }

    @Test
    void frissites_nemLetezoId_kivetelt_dob() {
        when(ugyfelRepository.findById(99L)).thenReturn(Optional.empty());

        UgyfelEntity modositott = ervenyesUgyfel();

        assertThatThrownBy(() -> ugyfelService.frissites(99L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen ügyfél: 99");
    }

    @Test
    void torles_letezoId_meghivjaADeleteById() {
        when(ugyfelRepository.existsById(1L)).thenReturn(true);

        ugyfelService.torles(1L);

        verify(ugyfelRepository).deleteById(1L);
    }

    @Test
    void torles_nemLetezoId_kivetelt_dob() {
        when(ugyfelRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> ugyfelService.torles(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen ügyfél: 99");
    }
}