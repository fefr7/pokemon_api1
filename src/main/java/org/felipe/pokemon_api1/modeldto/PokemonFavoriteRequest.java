package org.felipe.pokemon_api1.modeldto;

import lombok.Data;

@Data
public class PokemonFavoriteRequest {
    private Boolean favorite;
    private String note;
}
