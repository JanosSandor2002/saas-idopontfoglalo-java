package com.jantsee.idopontfoglalo.naptar.munkavallalo;

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
class MunkavallaloServiceTest {

    @Mock
    private MunkavallaloRepository munkavallaloRepository;

    @InjectMocks
    private MunkavallaloService munkavallaloService;

    @Test
    void letrehozas_ervenyesNevvel_menti() {
        MunkavallaloEntity uj = MunkavallaloEntity.builder().nev("Kovács Anna").build();
        when(munkavallaloRepository.save(any(MunkavallaloEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        MunkavallaloEntity eredmeny = munkavallaloService.letrehozas(uj);

        assertThat(eredmeny.getNev()).isEqualTo("Kovács Anna");
    }

    @Test
    void letrehozas_nullNevvel_kivetelt_dob() {
        MunkavallaloEntity uj = MunkavallaloEntity.builder().nev(null).build();

        assertThatThrownBy(() -> munkavallaloService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void letrehozas_uresNevvel_kivetelt_dob() {
        MunkavallaloEntity uj = MunkavallaloEntity.builder().nev("   ").build();

        assertThatThrownBy(() -> munkavallaloService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void osszesLekerese_visszaadjaAzOsszesMunkavallalot() {
        List<MunkavallaloEntity> lista = List.of(
                MunkavallaloEntity.builder().id(1L).nev("Anna").build(),
                MunkavallaloEntity.builder().id(2L).nev("Béla").build()
        );
        when(munkavallaloRepository.findAll()).thenReturn(lista);

        List<MunkavallaloEntity> eredmeny = munkavallaloService.osszesLekerese();

        assertThat(eredmeny).hasSize(2);
    }

    @Test
    void lekeresIdAlapjan_letezoId_visszaadjaAMunkavallalot() {
        MunkavallaloEntity letezo = MunkavallaloEntity.builder().id(1L).nev("Anna").build();
        when(munkavallaloRepository.findById(1L)).thenReturn(Optional.of(letezo));

        MunkavallaloEntity eredmeny = munkavallaloService.lekeresIdAlapjan(1L);

        assertThat(eredmeny.getNev()).isEqualTo("Anna");
    }

    @Test
    void lekeresIdAlapjan_nemLetezoId_kivetelt_dob() {
        when(munkavallaloRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> munkavallaloService.lekeresIdAlapjan(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen munkavállaló: 99");
    }

    @Test
    void frissites_ervenyesNevvel_frissiti() {
        MunkavallaloEntity letezo = MunkavallaloEntity.builder().id(1L).nev("Anna").build();
        when(munkavallaloRepository.findById(1L)).thenReturn(Optional.of(letezo));
        when(munkavallaloRepository.save(any(MunkavallaloEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        MunkavallaloEntity modositott = MunkavallaloEntity.builder().nev("Anna Mária").build();

        MunkavallaloEntity eredmeny = munkavallaloService.frissites(1L, modositott);

        assertThat(eredmeny.getNev()).isEqualTo("Anna Mária");
    }

    @Test
    void frissites_uresNevvel_kivetelt_dob() {
        MunkavallaloEntity letezo = MunkavallaloEntity.builder().id(1L).nev("Anna").build();
        when(munkavallaloRepository.findById(1L)).thenReturn(Optional.of(letezo));

        MunkavallaloEntity modositott = MunkavallaloEntity.builder().nev("").build();

        assertThatThrownBy(() -> munkavallaloService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A név nem lehet üres vagy null");
    }

    @Test
    void frissites_nemLetezoId_kivetelt_dob() {
        when(munkavallaloRepository.findById(99L)).thenReturn(Optional.empty());

        MunkavallaloEntity modositott = MunkavallaloEntity.builder().nev("Anna").build();

        assertThatThrownBy(() -> munkavallaloService.frissites(99L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen munkavállaló: 99");
    }

    @Test
    void torles_letezoId_meghivjaADeleteById() {
        when(munkavallaloRepository.existsById(1L)).thenReturn(true);

        munkavallaloService.torles(1L);

        org.mockito.Mockito.verify(munkavallaloRepository).deleteById(1L);
    }

    @Test
    void torles_nemLetezoId_kivetelt_dob() {
        when(munkavallaloRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> munkavallaloService.torles(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen munkavállaló: 99");
    }
}