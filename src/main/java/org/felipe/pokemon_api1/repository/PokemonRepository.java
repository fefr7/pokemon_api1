package org.felipe.pokemon_api1.repository;

import org.felipe.pokemon_api1.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByIdPokeApi(Integer idPokeApi);

    Page<Pokemon> findByTypesContainingIgnoreCase(String type, Pageable pageable);
}
