package org.felipe.pokemon_api1.controller;

import org.felipe.pokemon_api1.model.Pokemon;
import org.felipe.pokemon_api1.modeldto.PokemonFavoriteRequest;
import org.felipe.pokemon_api1.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

    private final PokemonService service;

    public PokemonController(PokemonService service) {
        this.service = service;
    }

    @PostMapping("/cache/{nameOrId}")
    public ResponseEntity<Pokemon> cachePokemon(@PathVariable String nameOrId) {
        Pokemon cached = service.cachePokemon(nameOrId);
        return ResponseEntity.ok(cached);
    }

    @GetMapping
    public ResponseEntity<Page<Pokemon>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{idLocal}")
    public ResponseEntity<Pokemon> getDetails(@PathVariable Long idLocal) {
        return ResponseEntity.ok(service.getDetails(idLocal));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Pokemon>> searchByType(@RequestParam String type, Pageable pageable) {
        return ResponseEntity.ok(service.searchByType(type, pageable));
    }

    @PatchMapping("/{idLocal}/favorite")
    public ResponseEntity<Pokemon> updateFavorite(@PathVariable Long idLocal,
                                                  @RequestBody PokemonFavoriteRequest request) {
        return ResponseEntity.ok(service.updateFavorite(idLocal, request));
    }
}