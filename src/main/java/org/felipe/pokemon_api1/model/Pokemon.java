package org.felipe.pokemon_api1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;

    @Column(unique = true, nullable = false)
    private Integer idPokeApi;

    @Column(nullable = false)
    private String name;

    private Integer height;
    private Integer weight;

    private String firstAbility;

    @Column(length = 255)
    private String types;

    @Column(nullable = false)
    private LocalDateTime cachedAt;

    private Boolean favorite = false;

    @Column(length = 500)
    private String note;
}
