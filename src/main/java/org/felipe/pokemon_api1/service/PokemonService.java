package org.felipe.pokemon_api1.service;

import org.felipe.pokemon_api1.model.Pokemon;
import org.felipe.pokemon_api1.modeldto.PokeApiDetailResponse;
import org.felipe.pokemon_api1.modeldto.PokemonFavoriteRequest;
import org.felipe.pokemon_api1.repository.PokemonRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final PokemonRepository repository;
    private final RestTemplate restTemplate;

    private static final String POKEAPI_URL = "https://pokeapi.co/api/v2/pokemon/";

    public PokemonService(PokemonRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @CacheEvict(value = {"pokemonList", "pokemonDetails", "pokemonSearchByType"}, allEntries = true)
    public Pokemon cachePokemon(String nameOrId) {
        PokeApiDetailResponse apiResponse;
        try {
            apiResponse = restTemplate.getForObject(POKEAPI_URL + nameOrId.toLowerCase(), PokeApiDetailResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon não encontrado na PokeAPI.");
        }

        if (apiResponse == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter dados do Pokémon.");
        }

        Optional<Pokemon> existingPokemon = repository.findByIdPokeApi(apiResponse.getId());
        Pokemon pokemon = existingPokemon.orElseGet(Pokemon::new);

        pokemon.setIdPokeApi(apiResponse.getId());
        pokemon.setName(apiResponse.getName());
        pokemon.setHeight(apiResponse.getHeight());
        pokemon.setWeight(apiResponse.getWeight());

        String firstAbility = apiResponse.getAbilities() != null && !apiResponse.getAbilities().isEmpty()
                ? apiResponse.getAbilities().get(0).getAbility().getName()
                : null;
        pokemon.setFirstAbility(firstAbility);

        String typesCsv = apiResponse.getTypes() != null ?
                apiResponse.getTypes().stream()
                        .map(t -> t.getType().getName())
                        .collect(Collectors.joining(","))
                : "";
        pokemon.setTypes(typesCsv);

        pokemon.setCachedAt(LocalDateTime.now());

        return repository.save(pokemon);
    }


    @Cacheable("pokemonList")
    public Page<Pokemon> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Cacheable(value = "pokemonDetails", key = "#idLocal")
    public Pokemon getDetails(Long idLocal) {
        return repository.findById(idLocal)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon com idLocal " + idLocal + " não encontrado."));
    }


    @Cacheable("pokemonSearchByType")
    public Page<Pokemon> searchByType(String typeName, Pageable pageable) {
        return repository.findByTypesContainingIgnoreCase(typeName, pageable);
    }


    @CacheEvict(value = {"pokemonDetails", "pokemonList"}, key = "#idLocal")
    public Pokemon updateFavorite(Long idLocal, PokemonFavoriteRequest request) {
        Pokemon pokemon = getDetails(idLocal);

        pokemon.setFavorite(request.getFavorite());
        pokemon.setNote(request.getNote());

        return repository.save(pokemon);
    }
}