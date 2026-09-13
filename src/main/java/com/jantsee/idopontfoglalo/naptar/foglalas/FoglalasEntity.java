package com.jantsee.idopontfoglalo.naptar.foglalas;

import com.jantsee.idopontfoglalo.naptar.munkavallalo.MunkavallaloEntity;
import com.jantsee.idopontfoglalo.naptar.szolgaltatas.SzolgaltatasEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "foglalasok")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoglalasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "szolgaltatas_id", nullable = false)
    private SzolgaltatasEntity szolgaltatas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "munkavallalo_id", nullable = false)
    private MunkavallaloEntity munkavallalo;

    @Column(nullable = false)
    private LocalDateTime idopont;

    @Column(name = "ugyfel_nev", nullable = false)
    private String ugyfelNev;

    @Column(name = "ugyfel_email", nullable = false)
    private String ugyfelEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoglalasStatusz statusz;
}