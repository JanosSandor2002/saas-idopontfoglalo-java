package com.jantsee.idopontfoglalo.naptar.foglalas;

import com.jantsee.idopontfoglalo.naptar.munkavallalo.MunkavallaloEntity;
import com.jantsee.idopontfoglalo.naptar.szolgaltatas.SzolgaltatasEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoglalasServiceTest {

    @Mock
    private FoglalasRepository foglalasRepository;

    @InjectMocks
    private FoglalasService foglalasService;

    private SzolgaltatasEntity szolgaltatas;
    private MunkavallaloEntity munkavallalo;

    @BeforeEach
    void setUp() {
        szolgaltatas = SzolgaltatasEntity.builder()
                .id(1L)
                .nev("Hajvágás")
                .idotartamPerc(30)
                .ar(5000)
                .build();

        munkavallalo = MunkavallaloEntity.builder()
                .id(1L)
                .nev("Kovács Anna")
                .build();
    }

    private FoglalasEntity ervenyesFoglalas() {
        return FoglalasEntity.builder()
                .szolgaltatas(szolgaltatas)
                .munkavallalo(munkavallalo)
                .idopont(LocalDateTime.now().plusDays(1))
                .ugyfelNev("Teszt Elek")
                .ugyfelEmail("teszt@example.com")
                .build();
    }

    @Test
    void letrehozas_ervenyesAdatokkal_mentEsStatusztFoglaltraAllitja() {
        FoglalasEntity uj = ervenyesFoglalas();
        when(foglalasRepository.save(any(FoglalasEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        FoglalasEntity eredmeny = foglalasService.letrehozas(uj);

        assertThat(eredmeny.getStatusz()).isEqualTo(FoglalasStatusz.FOGLALT);
        verify(foglalasRepository, times(1)).save(uj);
    }

    @Test
    void letrehozas_hianyzoSzolgaltatassal_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setSzolgaltatas(null);

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A szolgáltatás megadása kötelező");
    }

    @Test
    void letrehozas_hianyzoMunkavallaloval_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setMunkavallalo(null);

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A munkavállaló megadása kötelező");
    }

    @Test
    void letrehozas_hianyzoIdoponttal_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setIdopont(null);

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az időpont megadása kötelező");
    }

    @Test
    void letrehozas_multbeliIdoponttal_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setIdopont(LocalDateTime.now().minusDays(1));

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az időpont nem lehet a múltban");
    }

    @Test
    void letrehozas_uresUgyfelNevvel_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setUgyfelNev("  ");

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ügyfél neve nem lehet üres vagy null");
    }

    @Test
    void letrehozas_hianyzoUgyfelEmaillel_kivetelt_dob() {
        FoglalasEntity uj = ervenyesFoglalas();
        uj.setUgyfelEmail(null);

        assertThatThrownBy(() -> foglalasService.letrehozas(uj))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az ügyfél e-mail címe nem lehet üres vagy null");
    }

    @Test
    void osszesLekerese_visszaadjaAzOsszesFoglalast() {
        List<FoglalasEntity> lista = List.of(ervenyesFoglalas(), ervenyesFoglalas());
        when(foglalasRepository.findAll()).thenReturn(lista);

        List<FoglalasEntity> eredmeny = foglalasService.osszesLekerese();

        assertThat(eredmeny).hasSize(2);
    }

    @Test
    void lekeresIdAlapjan_letezoId_visszaadjaAFoglalast() {
        FoglalasEntity letezo = ervenyesFoglalas();
        letezo.setId(1L);
        when(foglalasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        FoglalasEntity eredmeny = foglalasService.lekeresIdAlapjan(1L);

        assertThat(eredmeny.getId()).isEqualTo(1L);
    }

    @Test
    void lekeresIdAlapjan_nemLetezoId_kivetelt_dob() {
        when(foglalasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> foglalasService.lekeresIdAlapjan(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nincs ilyen foglalás: 99");
    }

    @Test
    void frissites_ervenyesAdatokkal_frissitiAzIdopontot() {
        FoglalasEntity letezo = ervenyesFoglalas();
        letezo.setId(1L);
        when(foglalasRepository.findById(1L)).thenReturn(Optional.of(letezo));
        when(foglalasRepository.save(any(FoglalasEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        LocalDateTime ujIdopont = LocalDateTime.now().plusDays(2);
        FoglalasEntity modositott = FoglalasEntity.builder().idopont(ujIdopont).build();

        FoglalasEntity eredmeny = foglalasService.frissites(1L, modositott);

        assertThat(eredmeny.getIdopont()).isEqualTo(ujIdopont);
    }

    @Test
    void frissites_multbeliIdoponttal_kivetelt_dob() {
        FoglalasEntity letezo = ervenyesFoglalas();
        letezo.setId(1L);
        when(foglalasRepository.findById(1L)).thenReturn(Optional.of(letezo));

        FoglalasEntity modositott = FoglalasEntity.builder()
                .idopont(LocalDateTime.now().minusDays(1))
                .build();

        assertThatThrownBy(() -> foglalasService.frissites(1L, modositott))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Az időpont nem lehet a múltban");
    }

    @Test
    void torles_letezoId_statusztLemondvaraAllitja() {
        FoglalasEntity letezo = ervenyesFoglalas();
        letezo.setId(1L);
        when(foglalasRepository.findById(1L)).thenReturn(Optional.of(letezo));
        when(foglalasRepository.save(any(FoglalasEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        foglalasService.torles(1L);

        assertThat(letezo.getStatusz()).isEqualTo(FoglalasStatusz.LEMONDVA);
        verify(foglalasRepository).save(letezo);
    }
}