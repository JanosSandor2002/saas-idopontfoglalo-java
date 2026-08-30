package com.jantsee.idopontfoglalo.naptar.szolgaltatas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "szolgaltatasok")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SzolgaltatasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nev;

    @Column(name = "idotartam_perc", nullable = false)
    private Integer idotartamPerc;

    @Column(nullable = false)
    private Integer ar;
}
